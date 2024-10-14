package com.pragma.users_microservice.application.constants;

public class RequestConstants {
    private RequestConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String NAME_MANDATORY_MESSAGE = "Name must be provided.";

    public static final String LAST_NAME_MANDATORY_MESSAGE = "Last name must be provided.";

    public static final String IDENTITY_DOCUMENT_MANDATORY_MESSAGE = "Identity document must be provided.";
    public static final String IDENTITY_DOCUMENT_REGULAR_EXPRESSION = "^\\d+$";
    public static final String INVALID_IDENTITY_DOCUMENT_MESSAGE = "Identity document must be numeric only.";

    public static final String PHONE_NUMBER_MANDATORY_MESSAGE = "Phone number must be provided.";
    public static final String PHONE_NUMBER_REGULAR_EXPRESSION = "^(\\+57)?\\d{10}$";
    public static final String INVALID_PHONE_NUMBER_MESSAGE = "Invalid phone number, it must have 10 digits.";

    public static final String BIRTHDATE_MANDATORY_MESSAGE = "Birthdate must be provided.";
    public static final String BIRTHDATE_FUTURE_MESSAGE = "Field birthdate can't be in the future.";

    public static final String EMAIL_MANDATORY_MESSAGE = "Email must be provided.";
    public static final String EMAIL_REGULAR_EXPRESSION = "^[a-zA-Z][\\w.-]*@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,3}(\\.[a-zA-Z]{2})?$";
    public static final String INVALID_EMAIL_STRUCTURE_MESSAGE = "Invalid email structure.";

    public static final String PASSWORD_MANDATORY_MESSAGE = "Password must be provided.";

    public static final String ROLE_MANDATORY_MESSAGE = "Role id must be provided.";
}