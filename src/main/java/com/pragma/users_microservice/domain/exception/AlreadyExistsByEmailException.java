package com.pragma.users_microservice.domain.exception;

public class AlreadyExistsByEmailException extends RuntimeException {
    public AlreadyExistsByEmailException(String message) {
        super(message);
    }
}
