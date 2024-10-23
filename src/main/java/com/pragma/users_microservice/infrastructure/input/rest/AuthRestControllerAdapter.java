package com.pragma.users_microservice.infrastructure.input.rest;

import com.pragma.users_microservice.application.dto.request.LoginRequest;
import com.pragma.users_microservice.application.dto.response.AuthResponse;
import com.pragma.users_microservice.application.handler.IAuthenticationHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthRestControllerAdapter {
    private final IAuthenticationHandler authenticationHandler;

    @PostMapping("/login/user")
    public ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody LoginRequest request) {
        AuthResponse tokenResponse = authenticationHandler.authenticationUser(request);
        return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
    }
}
