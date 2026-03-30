package org.agro.core.domain.exceptions;

public class RecursoNaoEncontradoException extends RegraDeNegocioException {

    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem, 404);
    }
}