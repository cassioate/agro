package org.agro.adapters.configs.swagger.annotations;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.agro.adapters.dtos.response.ErroResponseDTO;
import org.agro.adapters.dtos.response.OperacaoCreditoResponseDTO;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Busca uma operação de crédito pelo ID")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Operação encontrada",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = OperacaoCreditoResponseDTO.class),
                        examples = @ExampleObject(
                                name = "Sucesso",
                                value = """
                                        {
                                            "idOperacaoCredito": "a3f1b2c4-d5e6-f7a8-b9c0-d1e2f3a4b5c6",
                                            "idAssociado": 1,
                                            "valorOperacao": 5000,
                                            "segmento": "AGRO",
                                            "codigoProdutoCredito": "903C",
                                            "codigoConta": "0123456789",
                                            "areaBeneficiadaHa": 10.5,
                                            "dataHoraContratacao": "2024-03-14T10:00:00"
                                        }
                                        """
                        )
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Operação não encontrada",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErroResponseDTO.class),
                        examples = @ExampleObject(
                                name = "Não encontrado",
                                value = """
                                        {
                                            "status": 404,
                                            "mensagem": "Operação não encontrada para o id: a3f1b2c4-d5e6-f7a8-b9c0-d1e2f3a4b5c6",
                                            "timestamp": "2024-03-14T10:00:00"
                                        }
                                        """
                        )
                )
        )
})
public @interface GetBuscarOperacaoSwagger {
}