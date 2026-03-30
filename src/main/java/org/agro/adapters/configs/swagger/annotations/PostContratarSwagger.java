package org.agro.adapters.configs.swagger.annotations;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.agro.adapters.dtos.request.OperacaoCreditoRequestDTO;
import org.agro.adapters.dtos.response.ContratacaoResponseDTO;
import org.agro.adapters.dtos.response.ErroResponseDTO;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Contrata uma operação de crédito")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "201",
                description = "Operação contratada com sucesso",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ContratacaoResponseDTO.class),
                        examples = @ExampleObject(
                                name = "Sucesso",
                                value = """
                                        {
                                            "idOperacaoCredito": "a3f1b2c4-d5e6-f7a8-b9c0-d1e2f3a4b5c6"
                                        }
                                        """
                        )
                )
        ),
        @ApiResponse(
                responseCode = "409",
                description = "Operação duplicada",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErroResponseDTO.class),
                        examples = @ExampleObject(
                                name = "Conflito",
                                value = """
                                        {
                                            "status": 409,
                                            "mensagem": "Operação já está sendo processada.",
                                            "timestamp": "2024-03-14T10:00:00"
                                        }
                                        """
                        )
                )
        ),
        @ApiResponse(
                responseCode = "422",
                description = "Produto não permite contratação ou AGRO sem área",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErroResponseDTO.class),
                        examples = {
                                @ExampleObject(
                                        name = "Produto não permitido",
                                        value = """
                                                {
                                                    "status": 422,
                                                    "mensagem": "Produto não permite contratação para o segmento e valor informados.",
                                                    "timestamp": "2024-03-14T10:00:00"
                                                }
                                                """
                                ),
                                @ExampleObject(
                                        name = "AGRO sem área",
                                        value = """
                                                {
                                                    "status": 422,
                                                    "mensagem": "Operações AGRO exigem areaBeneficiadaHa preenchido e maior que zero.",
                                                    "timestamp": "2024-03-14T10:00:00"
                                                }
                                                """
                                )
                        }
                )
        ),
        @ApiResponse(
                responseCode = "503",
                description = "Serviço de produtos indisponível",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErroResponseDTO.class),
                        examples = @ExampleObject(
                                name = "Serviço indisponível",
                                value = """
                                        {
                                            "status": 503,
                                            "mensagem": "Serviço de produtos indisponível.",
                                            "timestamp": "2024-03-14T10:00:00"
                                        }
                                        """
                        )
                )
        )
})
@RequestBody(
        description = "Dados para contratação do crédito",
        required = true,
        content = @Content(
                schema = @Schema(implementation = OperacaoCreditoRequestDTO.class),
                examples = @ExampleObject(
                        name = "Exemplo AGRO",
                        value = """
                                {
                                    "idAssociado": 1,
                                    "valorOperacao": 5000,
                                    "segmento": "AGRO",
                                    "codigoProdutoCredito": "903C",
                                    "codigoConta": "0123456789",
                                    "areaBeneficiadaHa": 10.5
                                }
                                """
                )
        )
)
public @interface PostContratarSwagger {
}