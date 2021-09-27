package br.com.zupacademy.antonio.proposta.security;

public class Mascara {

    public static String documento(String documento) {
        return documento.replace(documento.substring(3, 12), "******");
    }

    public static String numeroCartao(String documento) {
        return documento.replace(documento.substring(0, 9), "******");
    }
}
