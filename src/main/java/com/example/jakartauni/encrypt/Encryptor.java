package com.example.jakartauni.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryptor {
    private static final String SHA_256 = "SHA-256";

    public static String encryptSha256(String input) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance(SHA_256);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }

        digest.update(input.getBytes());
        byte[] hash = digest.digest();

        StringBuilder stringBuilder = new StringBuilder();

        for (byte b : hash) {
            stringBuilder.append(Character.forDigit((b >> 4) & 0xF, 16));
            stringBuilder.append(Character.forDigit((b & 0xF), 16));
        }

        return stringBuilder.toString().toUpperCase();
    }

    private Encryptor() {
    }
}