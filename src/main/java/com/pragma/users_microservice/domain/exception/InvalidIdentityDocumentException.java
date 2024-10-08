package com.pragma.users_microservice.domain.exception;

public class InvalidIdentityDocumentException extends RuntimeException {
    public InvalidIdentityDocumentException(String message) {
        super(message);
    }
}
