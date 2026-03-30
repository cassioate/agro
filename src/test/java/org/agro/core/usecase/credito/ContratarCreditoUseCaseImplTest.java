package org.agro.core.usecase.credito;

import org.agro.core.domain.OperacaoCreditoDomain;
import org.agro.core.domain.enums.Segmento;
import org.agro.core.domain.enums.StatusCode;
import org.agro.core.domain.exceptions.RegraDeNegocioException;
import org.agro.core.domain.exceptions.ServicoIndisponivelException;
import org.agro.core.ports.out.logging.LoggerPort;
import org.agro.core.ports.out.usecase.LockPort;
import org.agro.core.ports.out.usecase.contratar.SalvarOperacaoCreditoComSocioPort;
import org.agro.core.ports.out.usecase.contratar.ValidarProdutoPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.agro.core.domain.enums.Segmento.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContratarCreditoUseCaseImplTest {

    @Mock
    private ValidarProdutoPort validarProdutoPort;
    @Mock
    private SalvarOperacaoCreditoComSocioPort salvarOperacaoCreditoComSocioPort;
    @Mock
    private LockPort lockPort;
    @Mock
    private LoggerPort logger;

    private ContratarCreditoUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new ContratarCreditoUseCaseImpl(validarProdutoPort, salvarOperacaoCreditoComSocioPort, lockPort, logger);
    }

    private OperacaoCreditoDomain criarDomainValido(Segmento segmento) {
        OperacaoCreditoDomain domain = new OperacaoCreditoDomain();
        domain.setIdAssociado(1L);
        domain.setValorOperacao(new BigDecimal("5000"));
        domain.setSegmento(segmento);
        domain.setCodigoProdutoCredito("101A");
        domain.setCodigoConta("1234567890");
        if (AGRO.equals(segmento)) {
            domain.setAreaBeneficiadaHa(new BigDecimal("100"));
        }
        return domain;
    }

    @Test
    void deveContratarComSucesso() {
        OperacaoCreditoDomain domain = criarDomainValido(PF);
        OperacaoCreditoDomain salvo = criarDomainValido(PF);
        UUID idEsperado = UUID.randomUUID();
        salvo.setIdOperacaoCredito(idEsperado);

        when(lockPort.tentarLock(anyString())).thenReturn(true);
        when(validarProdutoPort.verificar(any(), any(), any())).thenReturn(true);
        when(salvarOperacaoCreditoComSocioPort.salvar(any())).thenReturn(salvo);

        UUID resultado = useCase.executar(domain);

        assertEquals(idEsperado, resultado);
        verify(lockPort).liberarLock(anyString());
    }

    @Test
    void deveContratarSegmentoAgro() {
        OperacaoCreditoDomain domain = criarDomainValido(AGRO);
        OperacaoCreditoDomain salvo = criarDomainValido(AGRO);
        salvo.setIdOperacaoCredito(UUID.randomUUID());

        when(lockPort.tentarLock(anyString())).thenReturn(true);
        when(validarProdutoPort.verificar(any(), any(), any())).thenReturn(true);
        when(salvarOperacaoCreditoComSocioPort.salvar(any())).thenReturn(salvo);

        assertDoesNotThrow(() -> useCase.executar(domain));
    }

    @Test
    void deveLancarExcecaoQuandoAgroSemAreaBeneficiada() {
        OperacaoCreditoDomain domain = criarDomainValido(AGRO);
        domain.setAreaBeneficiadaHa(null);

        RegraDeNegocioException ex = assertThrows(RegraDeNegocioException.class, () -> useCase.executar(domain));

        assertEquals(StatusCode.UNPROCESSABLE_ENTITY.getCodigo(), ex.getStatusCode());
        verifyNoInteractions(lockPort, validarProdutoPort, salvarOperacaoCreditoComSocioPort);
    }

    @Test
    void deveLancarExcecaoQuandoAreaBeneficiadaZero() {
        OperacaoCreditoDomain domain = criarDomainValido(AGRO);
        domain.setAreaBeneficiadaHa(BigDecimal.ZERO);

        RegraDeNegocioException ex = assertThrows(RegraDeNegocioException.class, () -> useCase.executar(domain));

        assertEquals(StatusCode.UNPROCESSABLE_ENTITY.getCodigo(), ex.getStatusCode());
        verifyNoInteractions(lockPort, validarProdutoPort, salvarOperacaoCreditoComSocioPort);
    }

    @Test
    void deveLancarExcecaoQuandoLockNaoAdquirido() {
        OperacaoCreditoDomain domain = criarDomainValido(PF);
        when(lockPort.tentarLock(anyString())).thenReturn(false);

        RegraDeNegocioException ex = assertThrows(RegraDeNegocioException.class, () -> useCase.executar(domain));

        assertEquals(StatusCode.CONFLICT.getCodigo(), ex.getStatusCode());
        verifyNoInteractions(validarProdutoPort, salvarOperacaoCreditoComSocioPort);
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoPermiteContratacao() {
        OperacaoCreditoDomain domain = criarDomainValido(PF);
        when(lockPort.tentarLock(anyString())).thenReturn(true);
        when(validarProdutoPort.verificar(any(), any(), any())).thenReturn(false);

        RegraDeNegocioException ex = assertThrows(RegraDeNegocioException.class, () -> useCase.executar(domain));

        assertEquals(StatusCode.UNPROCESSABLE_ENTITY.getCodigo(), ex.getStatusCode());
        verify(lockPort).liberarLock(anyString());
    }

    @Test
    void deveLiberarLockMesmoQuandoOcorreExcecao() {
        OperacaoCreditoDomain domain = criarDomainValido(PF);
        when(lockPort.tentarLock(anyString())).thenReturn(true);
        when(validarProdutoPort.verificar(any(), any(), any())).thenThrow(new ServicoIndisponivelException("serviço fora"));

        assertThrows(ServicoIndisponivelException.class, () -> useCase.executar(domain));

        verify(lockPort).liberarLock(anyString());
    }
}
