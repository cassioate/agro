package org.agro.adapters.out.persistence.adapter.contratar;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.agro.core.domain.OperacaoCreditoDomain;
import org.agro.core.domain.enums.StatusCode;
import org.agro.core.domain.exceptions.ViolacaoException;
import org.agro.core.ports.out.logging.LoggerPort;
import org.agro.core.ports.out.usecase.contratar.SalvarOperacaoCreditoComSocioPort;
import org.springframework.dao.DataIntegrityViolationException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static org.agro.core.domain.enums.Segmento.PJ;

@Component
@RequiredArgsConstructor
public class SalvarOperacaoCreditoComSocioAdapter implements SalvarOperacaoCreditoComSocioPort {

    private final SalvarOperacaoCreditoAdapter salvarOperacaoAdapter;
    private final SalvarSocioBeneficiarioAdapter salvarSocioAdapter;
    private final EntityManager entityManager;
    private final LoggerPort logger;
    private static final String CONSTRAINT_NAME = "uk_operacao_negocio";

    @Override
    @Transactional
    public OperacaoCreditoDomain salvar(OperacaoCreditoDomain domain) {
        try {
            OperacaoCreditoDomain salvo = salvarOperacaoAdapter.salvar(domain);
            if (PJ.equals(domain.getSegmento())) {
                salvarSocioAdapter.salvar(
                        salvo.getIdOperacaoCredito(),
                        salvo.getIdAssociado()
                );
            }
            entityManager.flush();
            return salvo;
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            String mensagem = e.getMessage();

            if (mensagem != null && mensagem.contains(CONSTRAINT_NAME)) {
                logger.logWarn("[SALVAR-OPERAÇÃO-CREDITO] - Chave idempotente duplicada detectada. associado: {}, produto: {}, codigoConta: {}, valor: {}", domain.getIdAssociado(), domain.getCodigoProdutoCredito(), domain.getCodigoConta(), domain.getValorOperacao());
                throw new ViolacaoException("Operação já existe no banco de dados.", StatusCode.CONFLICT.getCodigo());
            }

            throw e;
        }
    }
}