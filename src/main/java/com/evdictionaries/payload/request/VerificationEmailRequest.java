package com.evdictionaries.payload.request;

public class VerificationEmailRequest {
    private String verification_code;

    public String getVerification_code() {
        return verification_code;
    }

    public void setVerification_code(String verification_code) {
        this.verification_code = verification_code;
    }
}
