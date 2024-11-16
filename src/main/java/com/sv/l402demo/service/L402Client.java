package com.sv.l402demo.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.sv.l402demo.util.L402ChallengeParser;

public class L402Client {
    public static void main(String[] args) {
        try {
            // Crear un cliente HTTP
            HttpClient client = HttpClient.newHttpClient();

            // Crear una solicitud GET
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/l402/challenge"))
                    .GET()
                    .build();

            // Enviar la solicitud y obtener la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Obtener el encabezado 'WWW-Authenticate'
            String challenge = response.headers()
                                       .firstValue("WWW-Authenticate")
                                       .orElse(null);

            if (challenge != null) {
                // Parsear el desafío
                L402ChallengeParser.ParsedChallenge parsed = L402ChallengeParser.parseChallenge(challenge);

                System.out.println("Macaroon: " + parsed.getMacaroon());
                System.out.println("Invoice: " + parsed.getInvoice());
            } else {
                System.out.println("No se encontró el encabezado WWW-Authenticate.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

