package br.com.zupacademy.antonio.proposta.proposta;

import br.com.zupacademy.antonio.proposta.validate.AnyCPFOrCNPJ;
import br.com.zupacademy.antonio.proposta.validate.UniqueValue;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Optional;

public class PropostaForm {

    @AnyCPFOrCNPJ
    @UniqueValue
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

    public Proposta converteParaModelProposta(PropostaRepository pRepo) {

        Optional<Proposta> proposta = pRepo.findByDocumento(this.documento);
        if (proposta.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

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
