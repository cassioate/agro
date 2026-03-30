package org.agro.adapters.dtos.request;

import org.agro.core.domain.enums.Segmento;

import java.math.BigDecimal;

public record OperacaoCreditoRequestDTO(
        Long idAssociado,
        BigDecimal valorOperacao,
        Segmento segmento,
        String codigoProdutoCredito,
        String codigoConta,
        BigDecimal areaBeneficiadaHa
) {
}