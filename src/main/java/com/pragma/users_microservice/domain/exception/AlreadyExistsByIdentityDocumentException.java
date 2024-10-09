package com.pragma.users_microservice.domain.exception;

public class AlreadyExistsByIdentityDocumentException extends RuntimeException {
    public AlreadyExistsByIdentityDocumentException(String message) {
        super(message);
    }
}
