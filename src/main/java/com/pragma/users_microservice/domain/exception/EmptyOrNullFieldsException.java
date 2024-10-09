package com.pragma.users_microservice.domain.exception;

public class EmptyOrNullFieldsException extends RuntimeException {
    public EmptyOrNullFieldsException(String message) {
        super(message);
    }
}
