package org.agro.adapters.dtos.response;

import java.time.LocalDateTime;

public record ErroResponseDTO(
        int status,
        String mensagem,
        LocalDateTime timestamp
) {}