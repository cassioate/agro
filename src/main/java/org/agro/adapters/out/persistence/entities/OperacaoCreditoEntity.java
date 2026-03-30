package org.agro.adapters.out.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "operacao_credito")
public class OperacaoCreditoEntity {

    @Id
    @UuidGenerator
    @Column(name = "id_operacao_credito", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID idOperacaoCredito;

    @Column(name = "id_associado", nullable = false)
    private Long idAssociado;

    @Column(name = "valor_operacao", nullable = false, precision = 15, scale = 2)
    private BigDecimal valorOperacao;

    @Column(name = "segmento", nullable = false, length = 4)
    private String segmento;

    @Column(name = "codigo_produto_credito", nullable = false, length = 4)
    private String codigoProdutoCredito;

    @Column(name = "codigo_conta", nullable = false, length = 10)
    private String codigoConta;

    @Column(name = "area_beneficiada_ha", precision = 15, scale = 4)
    private BigDecimal areaBeneficiadaHa;

    @Column(name = "data_hora_contratacao", nullable = false)
    private LocalDateTime dataHoraContratacao;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "chave_idempotente", nullable = false, length = 36, unique = true)
    private String chaveIdempotente;

}
