package org.agro.core.domain.util;

import org.agro.core.domain.enums.Segmento;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class ChaveIdempotente {
    public static String gerar (
            Long idAssociado,
            String codigoProduto,
            String codigoConta,
            BigDecimal valorOperacao,
            Segmento segmento,
            LocalDateTime dataHora
    ) {
        int janelaMinutos = dataHora.getMinute() / 5;

        String chave = idAssociado
                + "_" + codigoProduto
                + "_" + codigoConta
                + "_" + valorOperacao.toPlainString()
                + "_" + segmento
                + "_" + dataHora.format(DateTimeFormatter.ofPattern("yyyyMMddHH"))
                + "_" + janelaMinutos;

        return UUID.nameUUIDFromBytes(chave.getBytes(StandardCharsets.UTF_8)).toString();
    }
}
