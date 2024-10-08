package com.pragma.users_microservice.domain.constants;

public class UseCaseConstants {
    private UseCaseConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String EMAIL_REGULAR_EXPRESSION = "^[a-zA-Z][\\w.-]*@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,3}(\\.[a-zA-Z]{2})?$";
    public static final String PHONE_NUMBER_REGULAR_EXPRESSION = "^\\+([1-9]\\d{0,1})\\d{10}$|^([1-9]\\d{0,1})?\\d{10}$";
    public static final String IDENTITY_DOCUMENT_REGULAR_EXPRESSION = "^\\d+$";
    public static final int LEGAL_AGE = 18;
}
