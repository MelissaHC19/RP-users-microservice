package com.pragma.users_microservice.application.constants;

public class RequestConstants {
    private RequestConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String NAME_MANDATORY_MESSAGE = "Name must be provided.";

    public static final String LAST_NAME_MANDATORY_MESSAGE = "Last name must be provided.";

    public static final String IDENTITY_DOCUMENT_MANDATORY_MESSAGE = "Identity document must be provided.";

    public static final String PHONE_NUMBER_MANDATORY_MESSAGE = "Phone number must be provided.";

    public static final String BIRTHDATE_MANDATORY_MESSAGE = "Birthdate must be provided.";

    public static final String EMAIL_MANDATORY_MESSAGE = "Email must be provided.";

    public static final String PASSWORD_MANDATORY_MESSAGE = "Password must be provided.";

    public static final String ROLE_MANDATORY_MESSAGE = "Role id must be provided.";
}