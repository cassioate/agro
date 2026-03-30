package org.agro.core.ports.out.usecase.contratar;

import org.agro.core.domain.OperacaoCreditoDomain;

public interface SalvarOperacaoCreditoComSocioPort {
    OperacaoCreditoDomain salvar(OperacaoCreditoDomain domain);
}