package org.agro.adapters.in.mapper;

import org.agro.adapters.dtos.request.OperacaoCreditoRequestDTO;
import org.agro.adapters.dtos.response.OperacaoCreditoResponseDTO;
import org.agro.core.domain.OperacaoCreditoDomain;
import org.springframework.stereotype.Component;

@Component
public class OperacaoCreditoMapper {

    public OperacaoCreditoDomain toDomain(OperacaoCreditoRequestDTO dto) {
        OperacaoCreditoDomain operacaoCreditoDomain = new OperacaoCreditoDomain();
        operacaoCreditoDomain.setIdAssociado(dto.idAssociado());
        operacaoCreditoDomain.setValorOperacao(dto.valorOperacao());
        operacaoCreditoDomain.setSegmento(dto.segmento());
        operacaoCreditoDomain.setCodigoProdutoCredito(dto.codigoProdutoCredito());
        operacaoCreditoDomain.setCodigoConta(dto.codigoConta());
        operacaoCreditoDomain.setAreaBeneficiadaHa(dto.areaBeneficiadaHa());
        return operacaoCreditoDomain;
    }

    public OperacaoCreditoResponseDTO toResponseDTO(OperacaoCreditoDomain domain) {
        return new OperacaoCreditoResponseDTO(
                domain.getIdOperacaoCredito(),
                domain.getIdAssociado(),
                domain.getValorOperacao(),
                domain.getSegmento(),
                domain.getCodigoProdutoCredito(),
                domain.getCodigoConta(),
                domain.getAreaBeneficiadaHa(),
                domain.getDataHoraContratacao()
        );
    }
}