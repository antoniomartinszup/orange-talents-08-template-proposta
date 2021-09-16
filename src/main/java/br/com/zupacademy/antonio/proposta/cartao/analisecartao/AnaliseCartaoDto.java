package br.com.zupacademy.antonio.proposta.cartao.analisecartao;

import br.com.zupacademy.antonio.proposta.cartao.Cartao;
import br.com.zupacademy.antonio.proposta.proposta.Proposta;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AnaliseCartaoDto {

    @NotBlank
    private String id;

    @NotNull
    private String titular;

    @NotNull
    private LocalDateTime emitidoEm;

    @NotNull
    private BigDecimal limite;

    @Deprecated
    public AnaliseCartaoDto() {
    }

    public AnaliseCartaoDto(String id, String titular, LocalDateTime emitidoEm, BigDecimal limite) {
        this.id = id;
        this.titular = titular;
        this.emitidoEm = emitidoEm;
        this.limite = limite;
    }

    public Cartao converteParaCartao(Proposta proposta) {
        return new Cartao(this.id, this.titular, this.emitidoEm, this.limite, proposta);
    }

    public String getId() {
        return id;
    }

    public String getTitular() {
        return titular;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public BigDecimal getLimite() {
        return limite;
    }
}