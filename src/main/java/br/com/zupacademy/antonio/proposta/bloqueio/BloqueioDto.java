package br.com.zupacademy.antonio.proposta.bloqueio;

import java.time.LocalDateTime;

public class BloqueioDto {

    private String id;
    private LocalDateTime bloqueioEm = LocalDateTime.now();
    private String clientIp;
    private String agentUser;
    private String idBloqueado;

    public BloqueioDto(Bloqueio bloqueio) {
        this.id = bloqueio.getId();
        this.bloqueioEm = bloqueio.getBloqueioEm();
        this.clientIp = bloqueio.getClientIp();
        this.agentUser = bloqueio.getAgentUser();
        this.idBloqueado = bloqueio.getCartao().getId();
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

    public String getIdBloqueado() {
        return idBloqueado;
    }
}
