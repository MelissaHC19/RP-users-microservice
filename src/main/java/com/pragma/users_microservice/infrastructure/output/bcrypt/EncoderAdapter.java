package com.pragma.users_microservice.infrastructure.output.bcrypt;

import com.pragma.users_microservice.domain.spi.IEncoderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
public class EncoderAdapter implements IEncoderPort {
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public String passwordEncoder(String password) {
        return passwordEncoder.encode(password);
    }
}
