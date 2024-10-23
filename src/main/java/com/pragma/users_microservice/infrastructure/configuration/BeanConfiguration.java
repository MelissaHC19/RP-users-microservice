package com.pragma.users_microservice.infrastructure.configuration;

import com.pragma.users_microservice.domain.api.IAuthenticationServicePort;
import com.pragma.users_microservice.domain.api.IUserServicePort;
import com.pragma.users_microservice.domain.spi.IEncoderPort;
import com.pragma.users_microservice.domain.spi.ITokenProviderPort;
import com.pragma.users_microservice.domain.spi.IUserPersistencePort;
import com.pragma.users_microservice.domain.usecase.AuthenticationUseCase;
import com.pragma.users_microservice.domain.usecase.UserUseCase;
import com.pragma.users_microservice.infrastructure.output.bcrypt.EncoderAdapter;
import com.pragma.users_microservice.infrastructure.output.jpa.adapter.UserJpaAdapter;
import com.pragma.users_microservice.infrastructure.output.jpa.mapper.IUserEntityMapper;
import com.pragma.users_microservice.infrastructure.output.jpa.repository.IUserRepository;
import com.pragma.users_microservice.infrastructure.security.adapter.TokenAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IUserEntityMapper userEntityMapper;
    private final IUserRepository userRepository;

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserJpaAdapter(userEntityMapper, userRepository);
    }

    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(userPersistencePort(), passwordEncoderPort());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public IEncoderPort passwordEncoderPort() {
        return new EncoderAdapter(passwordEncoder());
    }

    @Bean
    public ITokenProviderPort tokenProviderPort() {
        return new TokenAdapter();
    }

    @Bean
    public IAuthenticationServicePort authenticationServicePort() {
        return new AuthenticationUseCase(passwordEncoderPort(), userPersistencePort(), tokenProviderPort());
    }
}