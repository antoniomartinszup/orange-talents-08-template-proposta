package br.com.zupacademy.antonio.proposta.proposta;

import br.com.zupacademy.antonio.proposta.validate.AnyCPFOrCNPJ;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class PropostaForm {

    @AnyCPFOrCNPJ
    @NotBlank
    private String documento;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @NotNull
    @PositiveOrZero
    private BigDecimal salario;

    public PropostaForm(@AnyCPFOrCNPJ @NotBlank String documento,
                        @Email @NotBlank String email,
                        @NotBlank String nome,
                        @NotBlank String endereco,
                        @NotNull @PositiveOrZero BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Proposta converteParaModelProposta() {
        return new Proposta(this.documento, this.email, this.nome, this.endereco, this.salario);
    }

    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }
}
