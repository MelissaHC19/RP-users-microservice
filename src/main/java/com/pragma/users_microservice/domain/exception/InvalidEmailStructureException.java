package com.pragma.users_microservice.domain.exception;

public class InvalidEmailStructureException extends RuntimeException {
    public InvalidEmailStructureException(String message) {
        super(message);
    }
}
