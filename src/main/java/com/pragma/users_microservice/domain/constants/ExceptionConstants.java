package com.pragma.users_microservice.domain.constants;

public class ExceptionConstants {
    private ExceptionConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String INVALID_EMAIL_STRUCTURE_MESSAGE = "Invalid email structure.";
    public static final String INVALID_PHONE_NUMBER_MESSAGE = "Invalid phone number.";
    public static final String INVALID_IDENTITY_DOCUMENT_MESSAGE = "Identity document must be numeric only.";
    public static final String UNDERAGE_USER_MESSAGE = "Owner must be of legal age.";
    public static final String ALREADY_EXISTS_BY_IDENTITY_DOCUMENT_MESSAGE = "Owner already exists.";
}
