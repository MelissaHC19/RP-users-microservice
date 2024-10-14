package com.pragma.users_microservice.infrastructure.exceptionhandler;

import com.pragma.users_microservice.application.dto.response.ExceptionResponse;
import com.pragma.users_microservice.domain.exception.*;
import com.pragma.users_microservice.infrastructure.constants.ControllerConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(UnderageUserException.class)
    public ResponseEntity<ExceptionResponse> handleUnderageUserException(UnderageUserException exception) {
        ExceptionResponse response = new ExceptionResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(AlreadyExistsByIdentityDocumentException.class)
    public ResponseEntity<ExceptionResponse> handleAlreadyExistsByIdentityDocumentException(AlreadyExistsByIdentityDocumentException exception) {
        ExceptionResponse response = new ExceptionResponse(exception.getMessage(), HttpStatus.CONFLICT.toString(), LocalDateTime.now());
        return ResponseEntity.status(409).body(response);
    }

    @ExceptionHandler(AlreadyExistsByEmailException.class)
    public ResponseEntity<ExceptionResponse> handleAlreadyExistsByEmailException(AlreadyExistsByEmailException exception) {
        ExceptionResponse response = new ExceptionResponse(exception.getMessage(), HttpStatus.CONFLICT.toString(), LocalDateTime.now());
        return ResponseEntity.status(409).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptionsDTO(MethodArgumentNotValidException exception) {
        ArrayList<String> errors = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(
                error -> errors.add(error.getDefaultMessage())
        );
        Map<String, Object> response = new HashMap<>();
        response.put(ControllerConstants.TIMESTAMP, LocalDateTime.now().toString());
        response.put(ControllerConstants.ERRORS, errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}