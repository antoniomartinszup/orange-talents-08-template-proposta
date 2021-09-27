package br.com.zupacademy.antonio.proposta.proposta;

import br.com.zupacademy.antonio.proposta.cartao.Cartao;
import br.com.zupacademy.antonio.proposta.proposta.analiseproposta.AnaliseFinanceiraFeign;
import br.com.zupacademy.antonio.proposta.proposta.analiseproposta.AnalisePropostaDto;
import br.com.zupacademy.antonio.proposta.proposta.analiseproposta.AnalisePropostaForm;
import br.com.zupacademy.antonio.proposta.security.CryptoUtil;
import br.com.zupacademy.antonio.proposta.validate.AnyCPFOrCNPJ;
import feign.FeignException;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Enumerated(EnumType.STRING)
    private PropostaStatus propostaStatus;

    @OneToOne(cascade = CascadeType.ALL)
    private Cartao cartao;

    @Deprecated
    public Proposta() {
    }

    public Proposta(@AnyCPFOrCNPJ @NotBlank String documento,
                    @Email  @NotBlank String email,
                    @NotBlank String nome,
                    @NotBlank String endereco,
                    @NotNull @PositiveOrZero BigDecimal salario) {
        this.documento = CryptoUtil.encrypt(documento);
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public void propostaStatus(AnaliseFinanceiraFeign analiseFinanceiraFeign) {
        try {
            AnalisePropostaForm analisePropostaForm = new AnalisePropostaForm(getDocumento(), getNome(), getId());
            AnalisePropostaDto analisePropostaDto = analiseFinanceiraFeign.buscaAprovacao(analisePropostaForm);
            if (analisePropostaDto.getResultadoSolicitacao().equals("SEM_RESTRICAO")) {
                setPropostaStatus(PropostaStatus.ELEGIVEL);
            }
        } catch (FeignException feignException) {
            setPropostaStatus(PropostaStatus.NAO_ELEGIVEL);
        }
    }

    public Long getId() {
        return id;
    }

    public String getDocumento() {
        return CryptoUtil.decrypt(documento);
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

    public PropostaStatus getPropostaStatus() {
        return propostaStatus;
    }

    public void setPropostaStatus(PropostaStatus propostaStatus) {
        this.propostaStatus = propostaStatus;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }
}
