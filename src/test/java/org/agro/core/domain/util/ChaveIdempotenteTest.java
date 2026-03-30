package org.agro.core.domain.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.agro.core.domain.enums.Segmento.*;
import static org.junit.jupiter.api.Assertions.*;

class ChaveIdempotenteTest {

    @Test
    void deveGerarMesmaChaveParaMesmosParametros() {
        LocalDateTime dataHora = LocalDateTime.of(2024, 1, 15, 10, 3, 0);

        String chave1 = ChaveIdempotente.gerar(1L, "101A", "1234567890", new BigDecimal("5000"), PF, dataHora);
        String chave2 = ChaveIdempotente.gerar(1L, "101A", "1234567890", new BigDecimal("5000"), PF, dataHora);

        assertEquals(chave1, chave2);
    }

    @Test
    void deveGerarChavesDistintasParaAssociadosDiferentes() {
        LocalDateTime dataHora = LocalDateTime.of(2024, 1, 15, 10, 3, 0);

        String chave1 = ChaveIdempotente.gerar(1L, "101A", "1234567890", new BigDecimal("5000"), PF, dataHora);
        String chave2 = ChaveIdempotente.gerar(2L, "101A", "1234567890", new BigDecimal("5000"), PF, dataHora);

        assertNotEquals(chave1, chave2);
    }

    @Test
    void deveGerarChavesDistintasParaValoresDiferentes() {
        LocalDateTime dataHora = LocalDateTime.of(2024, 1, 15, 10, 3, 0);

        String chave1 = ChaveIdempotente.gerar(1L, "101A", "1234567890", new BigDecimal("5000"), PF, dataHora);
        String chave2 = ChaveIdempotente.gerar(1L, "101A", "1234567890", new BigDecimal("6000"), PF, dataHora);

        assertNotEquals(chave1, chave2);
    }

    @Test
    void deveGerarChavesDistintasParaSegmentosDiferentes() {
        LocalDateTime dataHora = LocalDateTime.of(2024, 1, 15, 10, 3, 0);

        String chave1 = ChaveIdempotente.gerar(1L, "101A", "1234567890", new BigDecimal("5000"), PF, dataHora);
        String chave2 = ChaveIdempotente.gerar(1L, "101A", "1234567890", new BigDecimal("5000"), AGRO, dataHora);

        assertNotEquals(chave1, chave2);
    }

    @Test
    void deveGerarMesmaChaveDentroDeJanelaDecincoMinutos() {
        LocalDateTime inicio = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
        LocalDateTime fimJanela = LocalDateTime.of(2024, 1, 15, 10, 4, 59);

        String chave1 = ChaveIdempotente.gerar(1L, "101A", "1234567890", new BigDecimal("5000"), PF, inicio);
        String chave2 = ChaveIdempotente.gerar(1L, "101A", "1234567890", new BigDecimal("5000"), PF, fimJanela);

        assertEquals(chave1, chave2);
    }

    @Test
    void deveGerarChavesDiferentesEmJanelasDistintas() {
        LocalDateTime janelaUm = LocalDateTime.of(2024, 1, 15, 10, 0, 0);
        LocalDateTime janelaDois = LocalDateTime.of(2024, 1, 15, 10, 5, 0);

        String chave1 = ChaveIdempotente.gerar(1L, "101A", "1234567890", new BigDecimal("5000"), PF, janelaUm);
        String chave2 = ChaveIdempotente.gerar(1L, "101A", "1234567890", new BigDecimal("5000"), PF, janelaDois);

        assertNotEquals(chave1, chave2);
    }
}
