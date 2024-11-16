package com.sv.l402demo.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AuthenticatedRequestExample {
    public static void main(String[] args) {
        // URL del recurso protegido
        String url = "https://api.fewsats.com/v0/storage/download/resource-id";
        
        // Obtener el macaroon y la preimagen desde los pasos anteriores
        String macaroon = "Location: https://api.fewsats.com/v0/storage/download/resource-id, Key: 1Z5WMFtB2jkBWKbMYaauBPLlwiG4T7Mz/AN/hMpI2F4=, Identifier: AACXiZu2lBVy1cadhSjf82ur0J1nK7YraSqAFYj2HDyXM/NihRz6F0pMH7xDIWVn7h/3M1cVCAd4H1MRBtcS7i5/";
        String preimage = "2f84e22556af9919f695d7761f404e98ff98058b7d32074de8c0c83bf63eecd7";

        try {
            // Crear la solicitud HTTP con cabeceras
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "L402 " + macaroon + ":" + preimage)
                .GET()
                .build();

            // Enviar la solicitud y obtener la respuesta
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Mostrar la respuesta
            System.out.println("Respuesta del servidor: " + response.body());
            System.out.println("Código de estado: " + response.statusCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


