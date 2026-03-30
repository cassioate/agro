package org.agro.adapters.out.persistence.mapper;

import org.agro.adapters.out.persistence.entities.OperacaoCreditoEntity;
import org.agro.core.domain.OperacaoCreditoDomain;
import org.agro.core.domain.enums.Segmento;
import org.springframework.stereotype.Component;

@Component
public class OperacaoCreditoEntityMapper {

    public OperacaoCreditoEntity toEntity(OperacaoCreditoDomain domain) {
        return OperacaoCreditoEntity.builder()
                .idAssociado(domain.getIdAssociado())
                .valorOperacao(domain.getValorOperacao())
                .segmento(domain.getSegmento().name())
                .codigoProdutoCredito(domain.getCodigoProdutoCredito())
                .codigoConta(domain.getCodigoConta())
                .areaBeneficiadaHa(domain.getAreaBeneficiadaHa())
                .dataHoraContratacao(domain.getDataHoraContratacao())
                .chaveIdempotente(domain.getChaveIdempotente())
                .build();
    }

    public OperacaoCreditoDomain toDomain(OperacaoCreditoEntity entity) {
        OperacaoCreditoDomain domain = new OperacaoCreditoDomain();
        domain.setIdOperacaoCredito(entity.getIdOperacaoCredito());
        domain.setIdAssociado(entity.getIdAssociado());
        domain.setValorOperacao(entity.getValorOperacao());
        domain.setSegmento(Segmento.valueOf(entity.getSegmento()));
        domain.setCodigoProdutoCredito(entity.getCodigoProdutoCredito());
        domain.setCodigoConta(entity.getCodigoConta());
        domain.setAreaBeneficiadaHa(entity.getAreaBeneficiadaHa());
        domain.setDataHoraContratacao(entity.getDataHoraContratacao());
        domain.setChaveIdempotente(entity.getChaveIdempotente());
        return domain;
    }
}