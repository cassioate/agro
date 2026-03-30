package org.agro.core.domain.exceptions;

public class RegraDeNegocioException extends RuntimeException {

    private final int statusCode;

    public RegraDeNegocioException(String mensagem, int statusCode) {
        super(mensagem);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}