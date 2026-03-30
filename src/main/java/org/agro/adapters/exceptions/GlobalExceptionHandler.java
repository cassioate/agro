package org.agro.adapters.exceptions;

import lombok.RequiredArgsConstructor;
import org.agro.adapters.dtos.response.ErroResponseDTO;
import org.agro.core.domain.exceptions.*;
import org.agro.core.ports.out.logging.LoggerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final LoggerPort logger;

    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<ErroResponseDTO> handleRegraDeNegocio(RegraDeNegocioException ex) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(new ErroResponseDTO(
                        ex.getStatusCode(),
                        ex.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResponseDTO> handleRecursoNaoEncontrado(RecursoNaoEncontradoException ex) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(new ErroResponseDTO(
                        ex.getStatusCode(),
                        ex.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(ViolacaoException.class)
    public ResponseEntity<ErroResponseDTO> handleViolacao(ViolacaoException ex) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(new ErroResponseDTO(
                        ex.getStatusCode(),
                        ex.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(ServicoIndisponivelException.class)
    public ResponseEntity<ErroResponseDTO> handleServicoIndisponivel(ServicoIndisponivelException ex) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new ErroResponseDTO(
                        HttpStatus.SERVICE_UNAVAILABLE.value(),
                        ex.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponseDTO> handleGenerico(Exception ex) {
        logger.logError("Erro inesperado: {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErroResponseDTO(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Erro interno do servidor.",
                        LocalDateTime.now()
                ));
    }
}