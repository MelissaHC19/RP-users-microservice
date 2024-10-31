package com.pragma.users_microservice.infrastructure.constants;

public class SecurityConstants {
    private SecurityConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String BEARER_HEADER = "Bearer ";
    public static final int BEGIN_INDEX = 7;
    public static final String SECRET_KEY_NAME = "secret_key";
    public static final String SECRET_KEY = System.getenv(SECRET_KEY_NAME);
    public static final String USERNAME_NOT_FOUND_MESSAGE = "Username not found.";
    public static final String EXPIRED_TOKEN_MESSAGE = "{\"message\": \"Token has expired.\"}";
    public static final String INVALID_TOKEN_MESSAGE = "{\"message\": \"Invalid.\"}";
    public static final String CREATE_OWNER_PATH = "/user/create/owner";
    public static final String GET_OWNER_BY_ID_PATH = "/user/owner/{id}";
    public static final String LOGIN_PATH = "/auth/login/user";
    public static final String ROLE_ADMIN = "Admin";
    public static final int EXPIRES_AT = 1000;
    public static final String CLAIMS_SUB = "sub";
    public static final String CLAIMS_ROLE = "role";
    public static final String CLAIMS_EXP = "exp";
    public static final String AUTHORITY_ROLE_ = "ROLE_";
    public static final String CONTENT_TYPE = "application/json";
    public static final String FORBIDDEN_MESSAGE = "{\"message\": \"Access denied. You don't have permission to access this resource.\"}";
    public static final String SWAGGER_PATH = "/swagger-ui/**";
    public static final String SWAGGER_PATH_2 = "/swaggerDocs";
    public static final String SWAGGER_PATH_3 = "/v3/api-docs/**";
    public static final String SWAGGER_PATH_4 = "/swagger-ui.html";
    public static final String CREATE_EMPLOYEE_PATH = "user/create/employee";
    public static final String ROLE_OWNER = "Owner";
    public static final String CREATE_CLIENT_PATH = "user/create/client";
    public static final String GET_EMPLOYEES_RESTAURANT_PATH = "/user/employee/{employeeId}";
    public static final String ROLE_EMPLOYEE = "Employee";
}
