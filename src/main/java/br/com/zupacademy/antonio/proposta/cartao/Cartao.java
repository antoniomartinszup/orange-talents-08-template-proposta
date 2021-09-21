package br.com.zupacademy.antonio.proposta.cartao;

import br.com.zupacademy.antonio.proposta.biometria.Biometria;
import br.com.zupacademy.antonio.proposta.proposta.Proposta;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
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

    @Enumerated(EnumType.STRING)
    private CartaoStatus cartaoStatus = CartaoStatus.ATIVO;

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

    public void alteraStatusCartao() {
        this.cartaoStatus = CartaoStatus.BLOQUEADO;
    }

    public void verificaStatusCartao() {
        if (this.cartaoStatus == CartaoStatus.BLOQUEADO) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
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

    public CartaoStatus getCartaoStatus() {
        return cartaoStatus;
    }
}
