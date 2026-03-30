package org.agro.core.usecase.credito;

import org.agro.core.domain.OperacaoCreditoDomain;
import org.agro.core.domain.enums.Segmento;
import org.agro.core.domain.exceptions.RecursoNaoEncontradoException;
import org.agro.core.ports.out.logging.LoggerPort;
import org.agro.core.ports.out.usecase.buscar.BuscarOperacaoCreditoPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarOperacaoCreditoUseCaseImplTest {

    @Mock
    private BuscarOperacaoCreditoPort buscarOperacaoCreditoPort;
    @Mock
    private LoggerPort logger;

    private BuscarOperacaoCreditoUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new BuscarOperacaoCreditoUseCaseImpl(buscarOperacaoCreditoPort, logger);
    }

    @Test
    void deveBuscarOperacaoComSucesso() {
        UUID id = UUID.randomUUID();
        OperacaoCreditoDomain domain = new OperacaoCreditoDomain();
        domain.setIdOperacaoCredito(id);
        domain.setIdAssociado(1L);
        domain.setSegmento(Segmento.PF);
        domain.setValorOperacao(new BigDecimal("3000"));

        when(buscarOperacaoCreditoPort.buscar(id)).thenReturn(domain);

        OperacaoCreditoDomain resultado = useCase.executar(id);

        assertEquals(id, resultado.getIdOperacaoCredito());
        verify(buscarOperacaoCreditoPort).buscar(id);
    }

    @Test
    void devePropagarExcecaoQuandoOperacaoNaoEncontrada() {
        UUID id = UUID.randomUUID();
        when(buscarOperacaoCreditoPort.buscar(id))
                .thenThrow(new RecursoNaoEncontradoException("Operação não encontrada para o id: " + id));

        assertThrows(RecursoNaoEncontradoException.class, () -> useCase.executar(id));
    }
}
