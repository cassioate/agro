package org.agro.adapters.dtos.response;

import org.agro.core.domain.enums.Segmento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OperacaoCreditoResponseDTO(
        UUID idOperacaoCredito,
        Long idAssociado,
        BigDecimal valorOperacao,
        Segmento segmento,
        String codigoProdutoCredito,
        String codigoConta,
        BigDecimal areaBeneficiadaHa,
        LocalDateTime dataHoraContratacao
) {
}
