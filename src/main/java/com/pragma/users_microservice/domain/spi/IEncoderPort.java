package com.pragma.users_microservice.domain.spi;

public interface IEncoderPort {
    String passwordEncoder(String password);
    boolean matches(String rawPassword, String encodedPassword);
}