package br.com.zupacademy.antonio.proposta.security;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

public class CryptoUtil {

    private static final String salt = "e606bfd5cf9f198e";

    public static String encrypt(String documento) {
        TextEncryptor textEncryptor = Encryptors.queryableText("documento", salt);
        return textEncryptor.encrypt(documento);
    }

    public static String decrypt(String documento) {
        TextEncryptor textEncryptor = Encryptors.queryableText("documento", salt);
        return textEncryptor.decrypt(documento);
    }
}
