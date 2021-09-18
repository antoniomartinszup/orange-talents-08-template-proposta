package br.com.zupacademy.antonio.proposta.biometria;

import br.com.zupacademy.antonio.proposta.cartao.Cartao;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class BiometriaForm {

    @NotBlank
    @JsonProperty("fingerPrint")
    private String fingerPrint;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public BiometriaForm(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public Biometria converteParaModelBiometria(Cartao cartao) {
        return new Biometria(this.fingerPrint, cartao);
    }

    public String getFingerPrint() {
        return fingerPrint;
    }
}
