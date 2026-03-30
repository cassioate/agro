package org.agro.core.domain;

import org.agro.core.domain.enums.Segmento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class OperacaoCreditoDomain {

    private UUID idOperacaoCredito;
    private Long idAssociado;
    private BigDecimal valorOperacao;
    private Segmento segmento;
    private String codigoProdutoCredito;
    private String codigoConta;
    private BigDecimal areaBeneficiadaHa;
    private LocalDateTime dataHoraContratacao;
    private String chaveIdempotente;

    public OperacaoCreditoDomain() {
    }

    public String getCodigoConta() {
        return codigoConta;
    }

    public void setCodigoConta(String codigoConta) {
        this.codigoConta = codigoConta;
    }

    public UUID getIdOperacaoCredito() {
        return idOperacaoCredito;
    }

    public void setIdOperacaoCredito(UUID idOperacaoCredito) {
        this.idOperacaoCredito = idOperacaoCredito;
    }

    public Long getIdAssociado() {
        return idAssociado;
    }

    public void setIdAssociado(Long idAssociado) {
        this.idAssociado = idAssociado;
    }

    public BigDecimal getValorOperacao() {
        return valorOperacao;
    }

    public void setValorOperacao(BigDecimal valorOperacao) {
        this.valorOperacao = valorOperacao;
    }

    public Segmento getSegmento() {
        return segmento;
    }

    public void setSegmento(Segmento segmento) {
        this.segmento = segmento;
    }

    public String getCodigoProdutoCredito() {
        return codigoProdutoCredito;
    }

    public void setCodigoProdutoCredito(String codigoProdutoCredito) {
        this.codigoProdutoCredito = codigoProdutoCredito;
    }

    public BigDecimal getAreaBeneficiadaHa() {
        return areaBeneficiadaHa;
    }

    public void setAreaBeneficiadaHa(BigDecimal areaBeneficiadaHa) {
        this.areaBeneficiadaHa = areaBeneficiadaHa;
    }

    public LocalDateTime getDataHoraContratacao() {
        return dataHoraContratacao;
    }

    public void setDataHoraContratacao(LocalDateTime dataHoraContratacao) {
        this.dataHoraContratacao = dataHoraContratacao;
    }

    public String getChaveIdempotente() {
        return chaveIdempotente;
    }

    public void setChaveIdempotente(String chaveIdempotente) {
        this.chaveIdempotente = chaveIdempotente;
    }
}
