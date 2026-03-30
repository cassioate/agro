package org.agro.core.ports.out.usecase.contratar;

import java.util.UUID;

public interface SalvarSocioBeneficiarioPort {
    void salvar(UUID idOperacaoCredito, Long idAssociado);
}