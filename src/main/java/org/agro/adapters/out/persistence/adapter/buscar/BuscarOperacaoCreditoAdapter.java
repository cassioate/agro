package org.agro.adapters.out.persistence.adapter.buscar;

import lombok.RequiredArgsConstructor;
import org.agro.adapters.out.persistence.mapper.OperacaoCreditoEntityMapper;
import org.agro.adapters.out.persistence.repositories.jpa.OperacaoCreditoRepository;
import org.agro.core.domain.OperacaoCreditoDomain;
import org.agro.core.domain.exceptions.RecursoNaoEncontradoException;
import org.agro.core.ports.out.logging.LoggerPort;
import org.agro.core.ports.out.usecase.buscar.BuscarOperacaoCreditoPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BuscarOperacaoCreditoAdapter implements BuscarOperacaoCreditoPort {

    private final OperacaoCreditoRepository repository;
    private final OperacaoCreditoEntityMapper mapper;
    private final LoggerPort logger;

    @Override
    public OperacaoCreditoDomain buscar(UUID idOperacaoCredito) {
        return repository.findById(idOperacaoCredito)
                .map(mapper::toDomain)
                .orElseThrow(() -> {
                    logger.logWarn("[BUSCAR-OPERAÇÃO-CREDITO] - Operação credito não encontrada para o idOperacaoCredito {}", idOperacaoCredito);
                    return new RecursoNaoEncontradoException(
                            "Operação não encontrada para o id: " + idOperacaoCredito
                    );
                });
    }
}