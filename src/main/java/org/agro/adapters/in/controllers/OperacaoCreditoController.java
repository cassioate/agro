package org.agro.adapters.in.controllers;

import lombok.RequiredArgsConstructor;
import org.agro.adapters.configs.swagger.annotations.GetBuscarOperacaoSwagger;
import org.agro.adapters.configs.swagger.annotations.PostContratarSwagger;
import org.agro.adapters.dtos.request.OperacaoCreditoRequestDTO;
import org.agro.adapters.dtos.response.ContratacaoResponseDTO;
import org.agro.adapters.dtos.response.OperacaoCreditoResponseDTO;
import org.agro.adapters.in.mapper.OperacaoCreditoMapper;
import org.agro.core.domain.OperacaoCreditoDomain;
import org.agro.core.ports.in.BuscarOperacaoCreditoUseCase;
import org.agro.core.ports.in.ContratarCreditoUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/operacoes-credito")
public class OperacaoCreditoController {

    private final ContratarCreditoUseCase contratarCreditoUseCase;
    private final BuscarOperacaoCreditoUseCase buscarOperacaoCreditoUseCase;
    private final OperacaoCreditoMapper mapper;

    @PostContratarSwagger
    @PostMapping
    public ResponseEntity<ContratacaoResponseDTO> contratar(@RequestBody OperacaoCreditoRequestDTO request) {
        OperacaoCreditoDomain domain = mapper.toDomain(request);
        UUID idOperacao = contratarCreditoUseCase.executar(domain);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ContratacaoResponseDTO(idOperacao));
    }

    @GetBuscarOperacaoSwagger
    @GetMapping("/{idOperacaoCredito}")
    public ResponseEntity<OperacaoCreditoResponseDTO> buscar(@PathVariable UUID idOperacaoCredito) {
        OperacaoCreditoDomain domain = buscarOperacaoCreditoUseCase.executar(idOperacaoCredito);
        return ResponseEntity.ok(mapper.toResponseDTO(domain));
    }
}
