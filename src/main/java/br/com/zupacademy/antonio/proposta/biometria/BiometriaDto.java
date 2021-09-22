package br.com.zupacademy.antonio.proposta.biometria;

import java.time.LocalDateTime;

public class BiometriaDto {

    private LocalDateTime registroEm;
    private String titular;

    public BiometriaDto(Biometria biometria) {
        this.registroEm = biometria.getRegistroEm();
        this.titular = biometria.getCartao().getTitular();
    }

    public LocalDateTime getRegistroEm() {
        return registroEm;
    }

    public String getTitular() {
        return titular;
    }
}
