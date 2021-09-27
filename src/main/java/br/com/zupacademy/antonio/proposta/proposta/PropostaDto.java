package br.com.zupacademy.antonio.proposta.proposta;

import br.com.zupacademy.antonio.proposta.security.Mascara;

public class PropostaDto {

    private Long id;
    private String nome;
    private String documento;
    private PropostaStatus propostaStatus;

    public PropostaDto(Proposta proposta) {
        this.id = proposta.getId();
        this.nome = proposta.getNome();
        this.documento = Mascara.documento(proposta.getDocumento());
        this.propostaStatus = proposta.getPropostaStatus();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDocumento() {
        return documento;
    }

    public PropostaStatus getPropostaStatus() {
        return propostaStatus;
    }
}
