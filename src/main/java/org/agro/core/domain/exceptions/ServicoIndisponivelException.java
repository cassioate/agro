package org.agro.core.domain.exceptions;

public class ServicoIndisponivelException extends RuntimeException {

    public ServicoIndisponivelException(String mensagem) {
        super(mensagem);
    }
}