package br.com.zupacademy.antonio.proposta.proposta.analiseproposta;

import br.com.zupacademy.antonio.proposta.validate.AnyCPFOrCNPJ;

import javax.validation.constraints.NotBlank;

public class AnalisePropostaForm {

    @AnyCPFOrCNPJ
    @NotBlank
    private String documento;

    @NotBlank
    private String nome;

    @NotBlank
    private Long idProposta;

    public AnalisePropostaForm(String documento, String nome, Long idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public Long getIdProposta() {
        return idProposta;
    }
}
