package br.com.zupacademy.antonio.proposta.bloqueio;

import br.com.zupacademy.antonio.proposta.cartao.Cartao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Bloqueio {

    @Id
    private String id = UUID.randomUUID().toString();

    private LocalDateTime bloqueioEm = LocalDateTime.now();

    @NotEmpty
    private String clientIp;

    @NotEmpty
    private String agentUser;

    @OneToOne
    private Cartao cartao;

    @Deprecated
    public Bloqueio() {
    }

    public Bloqueio(String clientIp, String agentUser, Cartao cartao) {
        this.clientIp = clientIp;
        this.agentUser = agentUser;
        this.cartao = cartao;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getBloqueioEm() {
        return bloqueioEm;
    }

    public String getClientIp() {
        return clientIp;
    }

    public String getAgentUser() {
        return agentUser;
    }

    public Cartao getCartao() {
        return cartao;
    }
}
