package org.agro.core.usecase.credito;

import org.agro.core.domain.OperacaoCreditoDomain;
import org.agro.core.ports.in.BuscarOperacaoCreditoUseCase;
import org.agro.core.ports.out.logging.LoggerPort;
import org.agro.core.ports.out.usecase.buscar.BuscarOperacaoCreditoPort;

import java.util.UUID;

public class BuscarOperacaoCreditoUseCaseImpl implements BuscarOperacaoCreditoUseCase {

    private final BuscarOperacaoCreditoPort buscarOperacaoCreditoPort;
    private final LoggerPort logger;

    public BuscarOperacaoCreditoUseCaseImpl(BuscarOperacaoCreditoPort buscarOperacaoCreditoPort, LoggerPort logger) {
        this.buscarOperacaoCreditoPort = buscarOperacaoCreditoPort;
        this.logger = logger;
    }

    @Override
    public OperacaoCreditoDomain executar(UUID idOperacaoCredito) {
        logger.logInfo("[BUSCAR-OPERAÇÃO-CREDITO] - Iniciando busca - idOperacaoCredito: {}", idOperacaoCredito);
        return buscarOperacaoCreditoPort.buscar(idOperacaoCredito);
    }
}