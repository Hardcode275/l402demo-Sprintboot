package com.sv.l402demo.service;

import java.security.SecureRandom;

public class MockInvoiceProvider {

    public InvoiceResponse createInvoice(int amount, String currency, String description) {
        // Factura simulada
        String mockInvoice = "lnbc1pnn39r2pp5lqsy4xzs6x6zg2czcyu24xp6zwn2yxgjjw7vcj5g8zyc448a4hlsdqqcqzzgxqyz5vqrzjqwnvuc0u4txn35cafc7w94gxvq5p3cu9dd95f7hlrh0fvs46wpvhdhde43txttn2yqqqqqryqqqqthqqpysp5qqgmwgu54j9g6t8a6vppvj55x2gn70t9u9e2vnkqlrk6us6kvy8s9qrsgqx3c5dzhv86cc5kjq2ncz407zhnh8c0ujre396upzsu3eytwh6853uspnrxnv473y009p7j5kekdzre7zqcdk3ku2976cn5f58wehr6cqs6vwpd";
        String mockPaymentHash = generateRandomHash();
        return new InvoiceResponse(mockInvoice, mockPaymentHash);
    }

    private String generateRandomHash() {
        SecureRandom random = new SecureRandom();
        byte[] hash = new byte[32];
        random.nextBytes(hash);
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

    public static class InvoiceResponse {
        private final String invoice;
        private final String paymentHash;

        public InvoiceResponse(String invoice, String paymentHash) {
            this.invoice = invoice;
            this.paymentHash = paymentHash;
        }

        public String getInvoice() {
            return invoice;
        }

        public String getPaymentHash() {
            return paymentHash;
        }
    }
}

