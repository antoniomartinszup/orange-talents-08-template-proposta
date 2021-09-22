package br.com.zupacademy.antonio.proposta.bloqueio.analisebloqueio;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AnaliseBloqueioDto {

    @JsonProperty("resultado")
    private String resultado;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public AnaliseBloqueioDto(String resultado) {
        this.resultado = resultado;
    }

    public String getRespostaAnalise() {
        return resultado;
    }
}
