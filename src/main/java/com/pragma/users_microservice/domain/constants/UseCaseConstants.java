package com.pragma.users_microservice.domain.constants;

public class UseCaseConstants {
    private UseCaseConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final int LEGAL_AGE = 18;
    public static final String ROLE_OWNER = "Owner";
    public static final Long ROLE_ID_OWNER = 2L;

    public static final String CLAIMS_SUB = "sub";
    public static final String CLAIMS_ROLE = "role";
    public static final Long EXPIRES_AT = 1000L;
}
