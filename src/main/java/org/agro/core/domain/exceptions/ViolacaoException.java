package org.agro.core.domain.exceptions;

public class ViolacaoException extends RuntimeException {

    private final int statusCode;

    public ViolacaoException(String mensagem, int statusCode) {
        super(mensagem);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}