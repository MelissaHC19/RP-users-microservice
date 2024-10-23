package com.pragma.users_microservice.application.handler;

import com.pragma.users_microservice.application.dto.request.LoginRequest;
import com.pragma.users_microservice.application.dto.response.AuthResponse;

public interface IAuthenticationHandler {
    AuthResponse authenticationUser(LoginRequest loginRequest);
}
