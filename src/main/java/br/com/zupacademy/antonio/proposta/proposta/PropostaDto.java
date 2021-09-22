package br.com.zupacademy.antonio.proposta.proposta;

public class PropostaDto {

    private Long id;
    private String nome;
    private PropostaStatus propostaStatus;

    public PropostaDto(Proposta proposta) {
        this.id = proposta.getId();
        this.nome = proposta.getNome();
        this.propostaStatus = proposta.getPropostaStatus();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public PropostaStatus getPropostaStatus() {
        return propostaStatus;
    }
}
