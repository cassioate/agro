CREATE TABLE IF NOT EXISTS operacao_credito (
    id_operacao_credito     UUID            NOT NULL DEFAULT gen_random_uuid(),
    id_associado            BIGINT          NOT NULL,
    valor_operacao          NUMERIC(15, 2)  NOT NULL,
    segmento                VARCHAR(4)      NOT NULL,
    codigo_produto_credito  VARCHAR(4)      NOT NULL,
    codigo_conta            VARCHAR(10)     NOT NULL,
    area_beneficiada_ha     NUMERIC(15, 4),
    data_hora_contratacao   TIMESTAMP       NOT NULL,
    version                 BIGINT          NOT NULL DEFAULT 0,
    chave_idempotente       VARCHAR(36)     NOT NULL,

    CONSTRAINT pk_titulos_credito
        PRIMARY KEY (id_operacao_credito),

    CONSTRAINT uk_operacao_negocio
        UNIQUE (chave_idempotente)
);

