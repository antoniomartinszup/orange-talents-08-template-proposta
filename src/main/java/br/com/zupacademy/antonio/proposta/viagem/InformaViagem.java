package br.com.zupacademy.antonio.proposta.viagem;

import br.com.zupacademy.antonio.proposta.cartao.Cartao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class InformaViagem {

    @Id
    private String id = UUID.randomUUID().toString();

    @NotBlank
    private String destinoViagem;
    private LocalDateTime informaViagemEm = LocalDateTime.now();

    @NotNull
    @Future
    private LocalDate informaFimViagemEm;

    @NotBlank
    private String ip;

    @NotBlank
    private String userAgent;

    @NotNull
    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public InformaViagem() {
    }

    public InformaViagem(String destinoViagem, LocalDate informaFimViagemEm, String ip, String userAgent, Cartao cartao) {
        this.destinoViagem = destinoViagem;
        this.informaFimViagemEm = informaFimViagemEm;
        this.ip = ip;
        this.userAgent = userAgent;
        this.cartao = cartao;
    }

    public String getId() {
        return id;
    }

    public String getDestinoViagem() {
        return destinoViagem;
    }

    public LocalDateTime getInformaViagemEm() {
        return informaViagemEm;
    }

    public LocalDate getInformaFimViagemEm() {
        return informaFimViagemEm;
    }

    public String getIp() {
        return ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public Cartao getCartao() {
        return cartao;
    }
}
