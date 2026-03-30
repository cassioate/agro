CREATE TABLE socio_beneficiario (
    id_socio_beneficiario   UUID            NOT NULL DEFAULT gen_random_uuid(),
    id_operacao_credito     UUID            NOT NULL,
    id_associado            BIGINT          NOT NULL,

    CONSTRAINT pk_socio_beneficiario
        PRIMARY KEY (id_socio_beneficiario),

    CONSTRAINT fk_socio_operacao
        FOREIGN KEY (id_operacao_credito)
        REFERENCES operacao_credito (id_operacao_credito)
        ON DELETE CASCADE,

    CONSTRAINT uk_socio_por_operacao
        UNIQUE (id_operacao_credito, id_associado)
);
