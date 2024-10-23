package com.pragma.users_microservice.domain.usecase;

import com.pragma.users_microservice.domain.api.IAuthenticationServicePort;
import com.pragma.users_microservice.domain.constants.ExceptionConstants;
import com.pragma.users_microservice.domain.constants.UseCaseConstants;
import com.pragma.users_microservice.domain.exception.InvalidCredentialsException;
import com.pragma.users_microservice.domain.model.User;
import com.pragma.users_microservice.domain.spi.IEncoderPort;
import com.pragma.users_microservice.domain.spi.ITokenProviderPort;
import com.pragma.users_microservice.domain.spi.IUserPersistencePort;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationUseCase implements IAuthenticationServicePort {
    private final IEncoderPort encoderPort;
    private final IUserPersistencePort userPersistencePort;
    private final ITokenProviderPort tokenProviderPort;

    public AuthenticationUseCase(IEncoderPort encoderPort, IUserPersistencePort userPersistencePort, ITokenProviderPort tokenProviderPort) {
        this.encoderPort = encoderPort;
        this.userPersistencePort = userPersistencePort;
        this.tokenProviderPort = tokenProviderPort;
    }

    @Override
    public String authenticationUser(String email, String password) {
        User user = userPersistencePort.getUserByEmail(email);
        if (user == null) {
            throw new InvalidCredentialsException(ExceptionConstants.INVALID_CREDENTIALS_MESSAGE);
        }
        validatePassword(password, user.getPassword());
        Map<String, Object> claims = createClaims(user);

        return generateToken(String.valueOf(user.getId()), claims);
    }

    private void validatePassword (String providedPassword, String password) {
        boolean isPasswordValid = encoderPort.matches(providedPassword, password);
        if (!isPasswordValid) {
            throw new InvalidCredentialsException(ExceptionConstants.INVALID_CREDENTIALS_MESSAGE);
        }
    }

    private Map<String, Object> createClaims(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(UseCaseConstants.CLAIMS_SUB, String.valueOf(user.getId()));
        claims.put(UseCaseConstants.CLAIMS_ROLE, user.getRole().getName());

        return claims;
    }

    private String generateToken(String subject, Map<String, Object> claims) {
        LocalDateTime issuedAt = LocalDateTime.now();
        LocalDateTime expiresAt = issuedAt.plusMinutes(UseCaseConstants.EXPIRES_AT);

        return tokenProviderPort.generateToken(issuedAt, subject, expiresAt, claims);
    }
}
