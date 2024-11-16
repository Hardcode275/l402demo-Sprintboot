package com.sv.l402demo.service;

public class MacaroonValidator {

    // Simula la validación del macaroon y el preimage
    public boolean validateMacaroon(String macaroon, String preimage) {
        // En un sistema real, se verificará que el macaroon es válido y coincide con la preimage
        return "2f84e22556af9919f695d7761f404e98ff98058b7d32074de8c0c83bf63eecd7".equals(preimage);
    }
}

