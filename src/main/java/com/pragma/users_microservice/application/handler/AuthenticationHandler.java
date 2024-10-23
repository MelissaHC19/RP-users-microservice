package com.pragma.users_microservice.application.handler;

import com.pragma.users_microservice.application.dto.request.LoginRequest;
import com.pragma.users_microservice.application.dto.response.AuthResponse;
import com.pragma.users_microservice.domain.api.IAuthenticationServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationHandler implements IAuthenticationHandler {
    private final IAuthenticationServicePort authenticationServicePort;

    @Override
    public AuthResponse authenticationUser(LoginRequest loginRequest) {
        return new AuthResponse(authenticationServicePort.authenticationUser(loginRequest.getEmail(), loginRequest.getPassword()));
    }
}
