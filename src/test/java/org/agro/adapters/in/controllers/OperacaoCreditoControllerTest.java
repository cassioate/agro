package org.agro.adapters.in.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.agro.adapters.dtos.request.OperacaoCreditoRequestDTO;
import org.agro.adapters.dtos.response.OperacaoCreditoResponseDTO;
import org.agro.adapters.in.mapper.OperacaoCreditoMapper;
import org.agro.core.domain.OperacaoCreditoDomain;
import org.agro.core.domain.enums.Segmento;
import org.agro.core.domain.enums.StatusCode;
import org.agro.core.domain.exceptions.RegraDeNegocioException;
import org.agro.core.domain.exceptions.RecursoNaoEncontradoException;
import org.agro.core.domain.exceptions.ServicoIndisponivelException;
import org.agro.core.ports.in.BuscarOperacaoCreditoUseCase;
import org.agro.core.ports.in.ContratarCreditoUseCase;
import org.agro.core.ports.out.logging.LoggerPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.agro.core.domain.enums.Segmento.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OperacaoCreditoController.class)
class OperacaoCreditoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ContratarCreditoUseCase contratarCreditoUseCase;

    @MockitoBean
    private BuscarOperacaoCreditoUseCase buscarOperacaoCreditoUseCase;

    @MockitoBean
    private OperacaoCreditoMapper mapper;

    @MockitoBean
    private LoggerPort logger;

    private OperacaoCreditoRequestDTO requestValido(Segmento segmento) {
        return new OperacaoCreditoRequestDTO(
                1L,
                new BigDecimal("5000"),
                segmento,
                "101A",
                "1234567890",
                AGRO.equals(segmento) ? new BigDecimal("100") : null
        );
    }

    @Test
    void deveContratarERetornar201() throws Exception {
        UUID idOperacao = UUID.randomUUID();

        when(mapper.toDomain(any())).thenReturn(new OperacaoCreditoDomain());
        when(contratarCreditoUseCase.executar(any())).thenReturn(idOperacao);

        mockMvc.perform(post("/operacoes-credito")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestValido(PF))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idOperacaoCredito").value(idOperacao.toString()));
    }

    @Test
    void deveRetornar422QuandoProdutoNaoPermiteContratacao() throws Exception {
        when(mapper.toDomain(any())).thenReturn(new OperacaoCreditoDomain());
        when(contratarCreditoUseCase.executar(any()))
                .thenThrow(new RegraDeNegocioException("Produto não permite contratação para o segmento e valor informados.", StatusCode.UNPROCESSABLE_ENTITY.getCodigo()));

        mockMvc.perform(post("/operacoes-credito")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestValido(PF))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.mensagem").value("Produto não permite contratação para o segmento e valor informados."));
    }

    @Test
    void deveRetornar422QuandoAgroSemAreaBeneficiada() throws Exception {
        when(mapper.toDomain(any())).thenReturn(new OperacaoCreditoDomain());
        when(contratarCreditoUseCase.executar(any()))
                .thenThrow(new RegraDeNegocioException("Operações AGRO exigem areaBeneficiadaHa preenchido e maior que zero.", StatusCode.UNPROCESSABLE_ENTITY.getCodigo()));

        mockMvc.perform(post("/operacoes-credito")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestValido(AGRO))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.mensagem").value("Operações AGRO exigem areaBeneficiadaHa preenchido e maior que zero."));
    }

    @Test
    void deveRetornar409QuandoOperacaoDuplicada() throws Exception {
        when(mapper.toDomain(any())).thenReturn(new OperacaoCreditoDomain());
        when(contratarCreditoUseCase.executar(any()))
                .thenThrow(new RegraDeNegocioException("Operação já está sendo processada.", StatusCode.CONFLICT.getCodigo()));

        mockMvc.perform(post("/operacoes-credito")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestValido(PF))))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensagem").value("Operação já está sendo processada."));
    }

    @Test
    void deveRetornar503QuandoServicoProdutosIndisponivel() throws Exception {
        when(mapper.toDomain(any())).thenReturn(new OperacaoCreditoDomain());
        when(contratarCreditoUseCase.executar(any()))
                .thenThrow(new ServicoIndisponivelException("Serviço de produtos indisponível."));

        mockMvc.perform(post("/operacoes-credito")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestValido(PF))))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.mensagem").value("Serviço de produtos indisponível."));
    }

    @Test
    void deveBuscarOperacaoERetornar200() throws Exception {
        UUID id = UUID.randomUUID();
        OperacaoCreditoDomain domain = new OperacaoCreditoDomain();
        OperacaoCreditoResponseDTO response = new OperacaoCreditoResponseDTO(
                id, 1L, new BigDecimal("5000"), PF, "101A", "1234567890", null, LocalDateTime.now()
        );

        when(buscarOperacaoCreditoUseCase.executar(id)).thenReturn(domain);
        when(mapper.toResponseDTO(domain)).thenReturn(response);

        mockMvc.perform(get("/operacoes-credito/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idOperacaoCredito").value(id.toString()));
    }

    @Test
    void deveRetornar404QuandoOperacaoNaoEncontrada() throws Exception {
        UUID id = UUID.randomUUID();
        when(buscarOperacaoCreditoUseCase.executar(id))
                .thenThrow(new RecursoNaoEncontradoException("Operação não encontrada para o id: " + id));

        mockMvc.perform(get("/operacoes-credito/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value("Operação não encontrada para o id: " + id));
    }
}
