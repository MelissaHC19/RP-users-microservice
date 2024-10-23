package com.pragma.users_microservice.infrastructure.constants;

public class SecurityConstants {
    private SecurityConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String BEARER_HEADER = "Bearer ";
    public static final int BEGIN_INDEX = 7;
    public static final String SECRET_KEY = "7OCOuld01GpRMRt3J9KWw1hGcvKTtcbxxfvLNx8JF+g=";
    public static final String USERNAME_NOT_FOUND_MESSAGE = "Username not found.";
    public static final String EXPIRED_TOKEN_MESSAGE = "Token has expired.";
    public static final String INVALID_TOKEN_MESSAGE = "Invalid token.";
    public static final String CREATE_OWNER_PATH = "/user/create/owner";
    public static final String GET_OWNER_BY_ID_PATH = "/user/owner/{id}";
    public static final String LOGIN_PATH = "/auth/login/user";
    public static final String ROLE_ADMIN = "Admin";
    public static final int EXPIRES_AT = 1000;
    public static final String CLAIMS_SUB = "sub";
    public static final String CLAIMS_ROLE = "role";
    public static final String CLAIMS_EXP = "exp";
    public static final String AUTHORITY_ROLE_ = "ROLE_";
}
