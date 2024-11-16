package com.sv.l402demo.model;

public class InvoiceResponse {
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

