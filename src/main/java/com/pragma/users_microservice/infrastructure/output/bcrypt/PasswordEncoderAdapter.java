package com.pragma.users_microservice.infrastructure.output.bcrypt;

import com.pragma.users_microservice.domain.spi.IPasswordEncoderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
public class PasswordEncoderAdapter implements IPasswordEncoderPort {
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public String passwordEncoder(String password) {
        return passwordEncoder.encode(password);
    }
}
