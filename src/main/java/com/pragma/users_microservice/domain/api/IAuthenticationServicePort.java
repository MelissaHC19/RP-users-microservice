package com.pragma.users_microservice.domain.api;

public interface IAuthenticationServicePort {
    String authenticationUser(String email, String password);
}
