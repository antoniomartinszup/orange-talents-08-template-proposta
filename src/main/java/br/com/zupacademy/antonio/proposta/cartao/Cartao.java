package br.com.zupacademy.antonio.proposta.cartao;

import br.com.zupacademy.antonio.proposta.biometria.Biometria;
import br.com.zupacademy.antonio.proposta.proposta.Proposta;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cartao {

    @Id
    @NotBlank
    private String id;

    @NotNull
    private String titular;

    @NotNull
    private LocalDateTime geradoEm;

    @NotNull
    private BigDecimal limite;

    @NotNull
    @OneToOne(mappedBy = "cartao")
    private Proposta proposta;

    @OneToMany(mappedBy = "cartao")
    private List<Biometria> biometrias = new ArrayList<>();

    @Deprecated
    public Cartao() {
    }

    public Cartao(String id, String titular, LocalDateTime geradoEm, BigDecimal limite, Proposta proposta) {
        this.id = id;
        this.titular = titular;
        this.geradoEm = geradoEm;
        this.limite = limite;
        this.proposta = proposta;
    }

    public String getId() {
        return id;
    }

    public String getTitular() {
        return titular;
    }

    public LocalDateTime getGeradoEm() {
        return geradoEm;
    }

    public Proposta getProposta() {
        return proposta;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public List<Biometria> getBiometrias() {
        return biometrias;
    }
}
