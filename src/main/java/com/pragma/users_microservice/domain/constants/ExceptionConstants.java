package com.pragma.users_microservice.domain.constants;

public class ExceptionConstants {
    private ExceptionConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String UNDERAGE_USER_MESSAGE = "Owner must be of legal age.";
    public static final String ALREADY_EXISTS_BY_IDENTITY_DOCUMENT_MESSAGE = "Owner already exists by identity document.";
    public static final String ALREADY_EXISTS_BY_EMAIL_MESSAGE = "Owner already exists by email.";
}