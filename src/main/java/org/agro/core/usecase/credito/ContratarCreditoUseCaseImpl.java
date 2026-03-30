package org.agro.core.usecase.credito;

import org.agro.core.domain.OperacaoCreditoDomain;
import org.agro.core.domain.enums.StatusCode;
import org.agro.core.domain.exceptions.RegraDeNegocioException;
import org.agro.core.domain.util.ChaveIdempotente;
import org.agro.core.ports.in.ContratarCreditoUseCase;
import org.agro.core.ports.out.logging.LoggerPort;
import org.agro.core.ports.out.usecase.*;
import org.agro.core.ports.out.usecase.contratar.SalvarOperacaoCreditoComSocioPort;
import org.agro.core.ports.out.usecase.contratar.ValidarProdutoPort;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.agro.core.domain.enums.Segmento.AGRO;

public class ContratarCreditoUseCaseImpl implements ContratarCreditoUseCase {

    private final ValidarProdutoPort validarProdutoPort;
    private final SalvarOperacaoCreditoComSocioPort salvarOperacaoCreditoComSocioPort;
    private final LockPort lockPort;
    private final LoggerPort logger;

    public ContratarCreditoUseCaseImpl(ValidarProdutoPort validarProdutoPort, SalvarOperacaoCreditoComSocioPort salvarOperacaoCreditoComSocioPort, LockPort lockPort, LoggerPort logger) {
        this.validarProdutoPort = validarProdutoPort;
        this.salvarOperacaoCreditoComSocioPort = salvarOperacaoCreditoComSocioPort;
        this.lockPort = lockPort;
        this.logger = logger;
    }

    @Override
    public UUID executar(OperacaoCreditoDomain domain) {
        logger.logInfo("[CONTRATAR-CREDITO] - Iniciando contratação. associado: {}, produto: {}", domain.getIdAssociado(), domain.getCodigoProdutoCredito());

        validarAgro(domain);

        String lockKey = construirChaveLock(domain);
        if (!lockPort.tentarLock(lockKey)) {
            logger.logWarn("[CONTRATAR-CREDITO] - Operação duplicada bloqueada. associado: {}, produto: {}", domain.getIdAssociado(), domain.getCodigoProdutoCredito());
            throw new RegraDeNegocioException("Operação já está sendo processada.", StatusCode.CONFLICT.getCodigo());
        }

        try {
            validarContratacaoProduto(domain);
            preencherDadosContratacao(domain);

            OperacaoCreditoDomain salvo = salvarOperacaoCreditoComSocioPort.salvar(domain);
            logger.logInfo("[CONTRATAR-CREDITO] - Contratação concluída. idOperacaoCredito: {}, associado: {}, produto: {}, segmento: {}, valor: {}", salvo.getIdOperacaoCredito(), domain.getIdAssociado(), domain.getCodigoProdutoCredito(), domain.getSegmento(), domain.getValorOperacao());
            return salvo.getIdOperacaoCredito();
        } finally {
            lockPort.liberarLock(lockKey);
        }
    }

    private void validarContratacaoProduto(OperacaoCreditoDomain domain) {
        boolean permiteContratar = validarProdutoPort.verificar(domain.getCodigoProdutoCredito(), domain.getSegmento(), domain.getValorOperacao());

        if (!permiteContratar) {
            logger.logWarn("[CONTRATAR-CREDITO] - Produto não permite contratação. produto: {}, segmento: {}, valor: {}", domain.getCodigoProdutoCredito(), domain.getSegmento(), domain.getValorOperacao());
            throw new RegraDeNegocioException("Produto não permite contratação para o segmento e valor informados.", StatusCode.UNPROCESSABLE_ENTITY.getCodigo());
        }
    }

    private void preencherDadosContratacao(OperacaoCreditoDomain domain) {
        LocalDateTime agora = LocalDateTime.now();
        domain.setDataHoraContratacao(agora);
        domain.setChaveIdempotente(ChaveIdempotente.gerar(domain.getIdAssociado(), domain.getCodigoProdutoCredito(), domain.getCodigoConta(), domain.getValorOperacao(), domain.getSegmento(), agora));
    }

    private void validarAgro(OperacaoCreditoDomain domain) {
        if (!AGRO.equals(domain.getSegmento())) return;

        if (domain.getAreaBeneficiadaHa() == null || domain.getAreaBeneficiadaHa().signum() <= 0) {
            logger.logWarn("[CONTRATAR-CREDITO] - Operação AGRO sem área válida. associado: {}, produto: {}", domain.getIdAssociado(), domain.getCodigoProdutoCredito());
            throw new RegraDeNegocioException("Operações AGRO exigem areaBeneficiadaHa preenchido e maior que zero.", StatusCode.UNPROCESSABLE_ENTITY.getCodigo());
        }
    }

    private String construirChaveLock(OperacaoCreditoDomain domain) {
        return domain.getIdAssociado() + "_" + domain.getCodigoProdutoCredito() + "_" + domain.getCodigoConta() + "_" + domain.getValorOperacao().toPlainString();
    }
}