package br.com.zupacademy.antonio.proposta.viagem.analiseviagem;

import java.time.LocalDate;

public class AnaliseViagemForm {

    private String destino;
    private LocalDate validoAte;

    public AnaliseViagemForm(String destino, LocalDate validoAte) {
        this.destino = destino;
        this.validoAte = validoAte;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }
}
