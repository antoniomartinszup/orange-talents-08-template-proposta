package br.com.zupacademy.antonio.proposta.carteira;

import br.com.zupacademy.antonio.proposta.cartao.Cartao;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class CarteiraDigital {

    @Id
    private String id;

    @NotBlank
    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    private ModeloCarteira modeloCarteira;

    @NotNull
    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public CarteiraDigital() {
    }

    public CarteiraDigital(String id, String email, ModeloCarteira modeloCarteira, Cartao cartao) {
        this.id = id;
        this.email = email;
        this.modeloCarteira = modeloCarteira;
        this.cartao = cartao;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public ModeloCarteira getModeloCarteira() {
        return modeloCarteira;
    }

    public Cartao getCartao() {
        return cartao;
    }
}
