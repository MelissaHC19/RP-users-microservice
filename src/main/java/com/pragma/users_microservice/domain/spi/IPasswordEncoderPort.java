package com.pragma.users_microservice.domain.spi;

public interface IPasswordEncoderPort {
    String passwordEncoder(String password);
}
