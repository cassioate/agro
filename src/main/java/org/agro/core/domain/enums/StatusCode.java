package org.agro.core.domain.enums;

public enum StatusCode {

    UNPROCESSABLE_ENTITY(422),
    CONFLICT(409);

    private final int codigo;

    StatusCode(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
}