package org.agro.core.ports.in;

import org.agro.core.domain.OperacaoCreditoDomain;

import java.util.UUID;

public interface BuscarOperacaoCreditoUseCase {
    OperacaoCreditoDomain executar(UUID idOperacaoCredito);
}
