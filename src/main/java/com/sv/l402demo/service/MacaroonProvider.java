package com.sv.l402demo.service;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Service;

@Service
public class MacaroonProvider {

    public String createMacaroon(String paymentHash, String url) {
        byte[] tokenId = generateRandomBytes(32);
        byte[] rootKey = generateRandomBytes(32);

        ByteBuffer identifierBuffer = ByteBuffer.allocate(2 + 32 + 32);
        identifierBuffer.putShort((short) 0); // Versión
        identifierBuffer.put(hexStringToByteArray(paymentHash));
        identifierBuffer.put(tokenId);

        byte[] identifier = identifierBuffer.array();
        return serializeMacaroon(url, rootKey, identifier);
    }

    private String serializeMacaroon(String location, byte[] key, byte[] identifier) {
        String keyBase64 = Base64.getEncoder().encodeToString(key);
        String identifierBase64 = Base64.getEncoder().encodeToString(identifier);
        return String.format("Location: %s, Key: %s, Identifier: %s", location, keyBase64, identifierBase64);
    }

    private byte[] generateRandomBytes(int length) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return bytes;
    }

    private byte[] hexStringToByteArray(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }
}

