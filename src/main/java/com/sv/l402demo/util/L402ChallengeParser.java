package com.sv.l402demo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class L402ChallengeParser {

    public static ParsedChallenge parseChallenge(String challenge) {
        String macaroon = null;
        String invoice = null;

        // Expresiones regulares para macaroon e invoice
        Pattern macaroonPattern = Pattern.compile("macaroon=\"([^\"]+)\"");
        Pattern invoicePattern = Pattern.compile("invoice=\"([^\"]+)\"");

        Matcher macaroonMatcher = macaroonPattern.matcher(challenge);
        Matcher invoiceMatcher = invoicePattern.matcher(challenge);

        if (macaroonMatcher.find()) {
            macaroon = macaroonMatcher.group(1);
        }

        if (invoiceMatcher.find()) {
            invoice = invoiceMatcher.group(1);
        }

        return new ParsedChallenge(macaroon, invoice);
    }

    public static class ParsedChallenge {
        private final String macaroon;
        private final String invoice;

        public ParsedChallenge(String macaroon, String invoice) {
            this.macaroon = macaroon;
            this.invoice = invoice;
        }

        public String getMacaroon() {
            return macaroon;
        }

        public String getInvoice() {
            return invoice;
        }
    }
}

