package br.com.zupacademy.antonio.proposta.bloqueio;

import java.time.LocalDateTime;

public class BloqueioDto {

    private String id;
    private LocalDateTime bloqueioEm = LocalDateTime.now();
    private String idBloqueado;

    public BloqueioDto(Bloqueio bloqueio) {
        this.id = bloqueio.getId();
        this.bloqueioEm = bloqueio.getBloqueioEm();
        this.idBloqueado = bloqueio.getCartao().getId();
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getBloqueioEm() {
        return bloqueioEm;
    }

    public String getIdBloqueado() {
        return idBloqueado;
    }
}
