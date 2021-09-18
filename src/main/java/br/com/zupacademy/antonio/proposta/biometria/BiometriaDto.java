package br.com.zupacademy.antonio.proposta.biometria;

import java.time.LocalDateTime;

public class BiometriaDto {

    private String fingerPrint;
    private LocalDateTime registroEm;
    private String titular;

    public BiometriaDto(Biometria biometria) {
        this.fingerPrint = biometria.getFingerPrint();
        this.registroEm = biometria.getRegistroEm();
        this.titular = biometria.getCartao().getTitular();
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public LocalDateTime getRegistroEm() {
        return registroEm;
    }

    public String getTitular() {
        return titular;
    }
}
