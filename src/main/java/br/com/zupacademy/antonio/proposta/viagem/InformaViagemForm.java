package br.com.zupacademy.antonio.proposta.viagem;

import br.com.zupacademy.antonio.proposta.cartao.Cartao;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class InformaViagemForm {

    @NotBlank
    private String destinoViagem;

    @NotNull
    @Future
    private LocalDate informaFimViagemEm;

    public InformaViagemForm(String destinoViagem, LocalDate informaFimViagemEm) {
        this.destinoViagem = destinoViagem;
        this.informaFimViagemEm = informaFimViagemEm;
    }

    public InformaViagem converteParaModelInformaViagem(Cartao cartao, String ip, String userAgent) {
        return new InformaViagem(this.destinoViagem, this.informaFimViagemEm, ip, userAgent, cartao);
    }

    public String getDestinoViagem() {
        return destinoViagem;
    }

    public LocalDate getInformaFimViagemEm() {
        return informaFimViagemEm;
    }
}
