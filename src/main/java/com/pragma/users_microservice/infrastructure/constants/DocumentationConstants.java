package com.pragma.users_microservice.infrastructure.constants;


public class DocumentationConstants {
    private DocumentationConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String CREATE_USER_SUMMARY = "Create owner";
    public static final String CREATE_USER_DESCRIPTION = "This endpoint allows the creation of a new restaurant owner account by submitting required fields: first name, last name, numeric identity document, phone number (up to 13 characters, may include +), birthdate, valid email, and a password. To register, the user must be at least 18 years old.";

    public static final String GET_USER_SUMMARY = "Get owner by id";
    public static final String GET_USER_DESCRIPTION = "This endpoint returns true when the user exists and has owner role, returns false when the user exists but isn't an owner and returns not found status code when the user doesn't exists.";

    public static final String LOGIN_SUMMARY = "Authentication";
    public static final String LOGIN_DESCRIPTION = "This endpoint authenticates a user with valid credentials (email and password) and returns a token. If the credentials are incorrect or there's an error in the request, the appropriate error is returned.";

    public static final String CREATE_EMPLOYEE_SUMMARY = "Create employee";
    public static final String CREATE_EMPLOYEE_DESCRIPTION = "This endpoint allows the creation of a new restaurant employee account by submitting required fields: first name, last name, numeric identity document, phone number (up to 13 characters, may include +), valid email, and a password.";

    public static final String CREATE_CLIENT_SUMMARY = "Create client";
    public static final String CREATE_CLIENT_DESCRIPTION = "This endpoint allows the creation of a new client account by submitting required fields: first name, last name, numeric identity document, phone number (up to 13 characters, may include +), valid email, and a password.";

    public static final String USER_TAG = "User";

    public static final String CREATED_STATUS_CODE = "201";
    public static final String BAD_REQUEST_STATUS_CODE = "400";
    public static final String CONFLICT_STATUS_CODE = "409";
    public static final String OK_STATUS_CODE = "200";
    public static final String NOT_FOUND_STATUS_CODE = "404";
    public static final String UNAUTHORIZED_STATUS_CODE = "401";
    public static final String FORBIDDEN_STATUS_CODE = "403";

    public static final String CREATED_RESPONSE_CODE_DESCRIPTION = "User created successfully.";
    public static final String BAD_REQUEST_RESPONSE_CODE_DESCRIPTION = "User not created.";
    public static final String CONFLICT_RESPONSE_CODE_DESCRIPTION = "User already exists.";
    public static final String OK_RESPONSE_CODE_DESCRIPTION = "The request was successful, and the user's role validation is returned.";
    public static final String NOT_FOUND_RESPONSE_CODE_DESCRIPTION = "User not found with the provided id.";
    public static final String OK_RESPONSE_CODE_DESCRIPTION_LOGIN = "User authenticated successfully.";
    public static final String UNAUTHORIZED_RESPONSE_CODE_DESCRIPTION_LOGIN = "User not authorized.";
    public static final String FORBIDDEN_RESPONSE_CODE_DESCRIPTION_LOGIN = "User don't have permissions.";
}
