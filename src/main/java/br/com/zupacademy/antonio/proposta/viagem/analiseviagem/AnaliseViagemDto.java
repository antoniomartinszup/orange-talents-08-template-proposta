package br.com.zupacademy.antonio.proposta.viagem.analiseviagem;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AnaliseViagemDto {

    @JsonProperty("resultado")
    private String resultado;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public AnaliseViagemDto(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }
}
