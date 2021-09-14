package br.com.zupacademy.antonio.proposta.proposta;

import br.com.zupacademy.antonio.proposta.validate.AnyCPFOrCNPJ;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @AnyCPFOrCNPJ
    @NotBlank
    @Column(unique = true)
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

    @Deprecated
    public Proposta() {
    }

    public Proposta(@AnyCPFOrCNPJ @NotBlank String documento,
                    @Email  @NotBlank String email,
                    @NotBlank String nome,
                    @NotBlank String endereco,
                    @NotNull @PositiveOrZero BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Long getId() {
        return id;
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
