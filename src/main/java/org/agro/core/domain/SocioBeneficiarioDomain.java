package org.agro.core.domain;

import java.util.UUID;

public class SocioBeneficiarioDomain {

    private UUID idSocioBeneficiario;
    private UUID idOperacaoCredito;
    private Long idAssociado;

    public SocioBeneficiarioDomain(UUID idSocioBeneficiario, UUID idOperacaoCredito, Long idAssociado) {
        this.idSocioBeneficiario = idSocioBeneficiario;
        this.idOperacaoCredito = idOperacaoCredito;
        this.idAssociado = idAssociado;
    }

    public SocioBeneficiarioDomain() {
    }

    public UUID getIdSocioBeneficiario() {
        return idSocioBeneficiario;
    }

    public void setIdSocioBeneficiario(UUID idSocioBeneficiario) {
        this.idSocioBeneficiario = idSocioBeneficiario;
    }

    public UUID getIdOperacaoCredito() {
        return idOperacaoCredito;
    }

    public void setIdOperacaoCredito(UUID idOperacaoCredito) {
        this.idOperacaoCredito = idOperacaoCredito;
    }

    public Long getIdAssociado() {
        return idAssociado;
    }

    public void setIdAssociado(Long idAssociado) {
        this.idAssociado = idAssociado;
    }
}
