package com.sv.l402demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sv.l402demo.service.MacaroonProvider;
import com.sv.l402demo.service.MacaroonValidator;
import com.sv.l402demo.service.MockInvoiceProvider;
import com.sv.l402demo.service.MockPreimageProvider;
import com.sv.l402demo.util.L402ChallengeParser;
import com.sv.l402demo.util.L402ChallengeParser.ParsedChallenge;

@RestController
@RequestMapping("/api/l402")
public class L402Controller {

    private final MockInvoiceProvider invoiceProvider = new MockInvoiceProvider();
    private final MacaroonProvider macaroonProvider = new MacaroonProvider();
    private final MockPreimageProvider preimageProvider = new MockPreimageProvider();
    private final MacaroonValidator macaroonValidator = new MacaroonValidator();

    /**
     * Endpoint para obtener un desafío L402.
     */
    @GetMapping("/challenge")
    public ResponseEntity<Object> getL402Challenge() {
        // 1. Crear factura simulada
        MockInvoiceProvider.InvoiceResponse invoiceResponse = invoiceProvider.createInvoice(
                100, "USD", "L402 Challenge: Downloading a file");

        // 2. Crear macaroon usando el payment hash de la factura
        String macaroon = macaroonProvider.createMacaroon(
                invoiceResponse.getPaymentHash(),
                "https://api.fewsats.com/v0/storage/download/resource-id"
        );

        // 3. Construir el desafío L402
        String challenge = String.format("L402 macaroon=\"%s\", invoice=\"%s\"",
                macaroon, invoiceResponse.getInvoice());

        // 4. Usar el parser para analizar el desafío
        L402ChallengeParser challengeParser = new L402ChallengeParser();
        ParsedChallenge parsedChallenge = challengeParser.parseChallenge(challenge);

        // 5. Obtener la preimagen simulada
        String preimage = preimageProvider.getPreimage(parsedChallenge.getInvoice());

        // 6. Crear un mapa de respuesta JSON
        Map<String, String> response = new HashMap<>();
        response.put("macaroon", parsedChallenge.getMacaroon());
        response.put("invoice", parsedChallenge.getInvoice());
        response.put("preimage", preimage); // Incluir preimagen en la respuesta

        // 7. Devolver la respuesta como JSON
        return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED)
                             .body(response);
    }

    /**
     * Endpoint para validar una solicitud autenticada.
     */
    @GetMapping("/protected-resource")
    public ResponseEntity<Object> getProtectedResource(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {

        // 1. Validar si el encabezado Authorization está presente
        if (authorizationHeader == null || !authorizationHeader.startsWith("L402 ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body("Authorization header missing or invalid");
        }

        // 2. Extraer macaroon y preimage del encabezado
        String[] parts = authorizationHeader.substring(5).split(":");
        if (parts.length != 2) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Invalid Authorization format");
        }
        String macaroon = parts[0];
        String preimage = parts[1];

        // 3. Validar el macaroon y el preimage
        boolean isValid = macaroonValidator.validateMacaroon(macaroon, preimage);

        if (!isValid) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                 .body("Invalid macaroon or preimage");
        }

        // 4. Devolver el recurso protegido si es válido
        return ResponseEntity.ok("Access granted to the protected resource");
    }
}



