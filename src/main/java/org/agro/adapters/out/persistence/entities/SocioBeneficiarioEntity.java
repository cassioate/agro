package org.agro.adapters.out.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "socio_beneficiario")
public class SocioBeneficiarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_socio_beneficiario")
    private UUID idSocioBeneficiario;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_operacao_credito", nullable = false, foreignKey = @ForeignKey(name = "fk_socio_operacao"))
    private OperacaoCreditoEntity operacaoCredito;

    @Column(name = "id_associado", nullable = false)
    private Long idAssociado;

}
