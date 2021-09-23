package br.com.zupacademy.antonio.proposta.viagem;

import java.time.LocalDateTime;

public class InformaViagemDto {

    private String id;
    private LocalDateTime informaViagemEm = LocalDateTime.now();

    public InformaViagemDto(InformaViagem informaViagem) {
        this.id = informaViagem.getId();
        this.informaViagemEm = informaViagem.getInformaViagemEm();
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getInformaViagemEm() {
        return informaViagemEm;
    }
}
