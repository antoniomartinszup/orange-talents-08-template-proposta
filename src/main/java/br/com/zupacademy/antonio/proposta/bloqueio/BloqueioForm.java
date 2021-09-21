package br.com.zupacademy.antonio.proposta.bloqueio;

import br.com.zupacademy.antonio.proposta.cartao.Cartao;

import javax.validation.constraints.NotEmpty;

public class BloqueioForm {

    @NotEmpty
    private String clientIp;

    @NotEmpty
    private String agentUser;

    public BloqueioForm(String clientIp, String agentUser) {
        this.clientIp = clientIp;
        this.agentUser = agentUser;
    }

    public Bloqueio converteParaModelBloqueio(Cartao cartao) {
        return new Bloqueio(this.clientIp, this.agentUser, cartao);
    }

    public String getClientIp() {
        return clientIp;
    }

    public String getAgentUser() {
        return agentUser;
    }
}
