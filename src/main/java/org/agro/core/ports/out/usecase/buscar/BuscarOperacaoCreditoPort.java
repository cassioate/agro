package org.agro.core.ports.out.usecase.buscar;

import org.agro.core.domain.OperacaoCreditoDomain;

import java.util.UUID;

public interface BuscarOperacaoCreditoPort {
    OperacaoCreditoDomain buscar(UUID idOperacaoCredito);
}