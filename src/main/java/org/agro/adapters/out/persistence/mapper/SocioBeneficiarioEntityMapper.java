package org.agro.adapters.out.persistence.mapper;

import org.agro.adapters.out.persistence.entities.OperacaoCreditoEntity;
import org.agro.adapters.out.persistence.entities.SocioBeneficiarioEntity;
import org.springframework.stereotype.Component;

@Component
public class SocioBeneficiarioEntityMapper {

    public SocioBeneficiarioEntity toEntity(OperacaoCreditoEntity operacaoEntity, Long idAssociado) {
        return SocioBeneficiarioEntity.builder()
                .operacaoCredito(operacaoEntity)
                .idAssociado(idAssociado)
                .build();
    }
}