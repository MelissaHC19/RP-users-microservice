package com.pragma.users_microservice.infrastructure.constants;


public class DocumentationConstants {
    private DocumentationConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String CREATE_USER_SUMMARY = "Create user";
    public static final String CREATE_USER_DESCRIPTION = "This endpoint allows the creation of a new restaurant owner account by submitting required fields: first name, last name, numeric identity document, phone number (up to 13 characters, may include +), birthdate, valid email, and a password. To register, the user must be at least 18 years old.";

    public static final String GET_USER_SUMMARY = "Get user by id";
    public static final String GET_USER_DESCRIPTION = "This endpoints retrieves detailed information about a specific user by their unique ID";

    public static final String USER_TAG = "User";

    public static final String CREATED_STATUS_CODE = "201";
    public static final String BAD_REQUEST_STATUS_CODE = "400";
    public static final String CONFLICT_STATUS_CODE = "409";
    public static final String OK_STATUS_CODE = "200";
    public static final String NOT_FOUND_STATUS_CODE = "404";

    public static final String CREATED_RESPONSE_CODE_DESCRIPTION = "User created successfully.";
    public static final String BAD_REQUEST_RESPONSE_CODE_DESCRIPTION = "User not created.";
    public static final String CONFLICT_RESPONSE_CODE_DESCRIPTION = "User already exists.";
    public static final String OK_RESPONSE_CODE_DESCRIPTION = "The request was successful, and the user's details are returned in the response.";
    public static final String NOT_FOUND_RESPONSE_CODE_DESCRIPTION = "User not found with the provided id.";
}
