package org.agro.core.ports.out.usecase.contratar;

import org.agro.core.domain.enums.Segmento;

import java.math.BigDecimal;

public interface ValidarProdutoPort {
    boolean verificar(String codigoProduto, Segmento segmento, BigDecimal valor);
}