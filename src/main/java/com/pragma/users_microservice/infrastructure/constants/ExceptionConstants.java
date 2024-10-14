package com.pragma.users_microservice.infrastructure.constants;

public class ExceptionConstants {
    private ExceptionConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String USER_NOT_FOUND_MESSAGE = "User not found with id: %d";
}
