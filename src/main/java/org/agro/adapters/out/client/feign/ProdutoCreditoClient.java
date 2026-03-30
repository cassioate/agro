package org.agro.adapters.out.client.feign;

import org.agro.adapters.dtos.response.ProdutoPermiteContratarResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "produto-credito-client", url = "${cliente.produto-credito.url}")
public interface ProdutoCreditoClient {

    @GetMapping("/produtos-credito/{codigo}/permite-contratacao")
    ProdutoPermiteContratarResponseDTO verificarPermiteContratacao(@PathVariable("codigo") String codigo,
                                                                   @RequestParam("segmento") String segmento,
                                                                   @RequestParam("valorFinanciado") String valorFinanciado
    );
}