package org.agro.adapters.out.client.produto;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.agro.adapters.out.client.feign.ProdutoCreditoClient;
import org.agro.core.domain.enums.Segmento;
import org.agro.core.domain.exceptions.ServicoIndisponivelException;
import org.agro.core.ports.out.logging.LoggerPort;
import org.agro.core.ports.out.usecase.contratar.ValidarProdutoPort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
@RequiredArgsConstructor
public class ValidarProdutoAdapter implements ValidarProdutoPort {

    private final ProdutoCreditoClient produtoClient;
    private final LoggerPort logger;

    @Override
    @Retry(name = "produtoService", fallbackMethod = "fallback")
    @CircuitBreaker(name = "produtoService", fallbackMethod = "fallback")
    public boolean verificar(String codigoProduto, Segmento segmento, BigDecimal valor) {
        try {
            var response = produtoClient.verificarPermiteContratacao(codigoProduto, segmento.name(), valor.toPlainString());

            if (response == null) {
                throw new ServicoIndisponivelException("Serviço de produtos retornou resposta vazia.");
            }

            return Boolean.TRUE.equals(response.permiteContratar());
        } catch (ServicoIndisponivelException e) {
            throw e;
        } catch (Exception e) {
            logger.logError("Erro ao consultar serviço de produtos: {}", e.getMessage(), e);
            throw new ServicoIndisponivelException("Serviço de produtos indisponível.");
        }
    }

    private boolean fallback(String codigoProduto, Segmento segmento, BigDecimal valor, Exception e) {
        logger.logError("Serviço de produtos indisponível: codigo={}, segmento={}, valor={}",
                codigoProduto, segmento, valor, e);
        throw new ServicoIndisponivelException("Serviço de produtos indisponível.");
    }
}