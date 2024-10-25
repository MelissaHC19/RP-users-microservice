package com.pragma.users_microservice.domain.usecase;

import com.pragma.users_microservice.domain.constants.ExceptionConstants;
import com.pragma.users_microservice.domain.exception.InvalidCredentialsException;
import com.pragma.users_microservice.domain.model.Role;
import com.pragma.users_microservice.domain.model.User;
import com.pragma.users_microservice.domain.spi.IEncoderPort;
import com.pragma.users_microservice.domain.spi.ITokenProviderPort;
import com.pragma.users_microservice.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationUseCaseTest {
    @Mock
    private IEncoderPort encoderPort;

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private ITokenProviderPort tokenProviderPort;

    @InjectMocks
    private AuthenticationUseCase authenticationUseCase;

    @Test
    @DisplayName("Authenticates user successfully and returns token")
    void authenticationUser() {
        User user = new User(10L, "Pablo", "Casas", "99123456789",
                "+573233039679", LocalDate.parse("1990-05-15"), "pablo.casas@yahoo.com.co",
                "$2a$10$Mh8tJKUQ7RusWMkVXIpXb.PfuxMwGEDCkhIeXTKIGhmL9l/XsJemW", new Role(2L, null, null));
        String email = "pablo.casas@yahoo.com.co";
        String password = "owner123";
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMCIsInJvbGUiOiJPd25lciIsImlhdCI6MTcyOTc5MjQ4MywiZXhwIjoxNzI5ODUyNDgzfQ.e7dAxlrUrHcmZ6SCjIaImujnPfJefqtv1R6sg4Bh2Yk";
        Mockito.when(userPersistencePort.getUserByEmail(email)).thenReturn(user);
        Mockito.when(encoderPort.matches(password, user.getPassword())).thenReturn(true);
        Mockito.when(tokenProviderPort.generateToken(any(), anyString(), any(), anyMap())).thenReturn(token);
        String resultToken = authenticationUseCase.authenticationUser(email, password);
        assertEquals(token, resultToken);
        Mockito.verify(userPersistencePort, Mockito.times(1)).getUserByEmail(email);
        Mockito.verify(encoderPort, Mockito.times(1)).matches(password, user.getPassword());
        Mockito.verify(tokenProviderPort, Mockito.times(1)).generateToken(any(), eq(String.valueOf(user.getId())), any(), anyMap());
    }

    @Test
    @DisplayName("Validation exception when user not found by email")
    void authenticationUserShouldThrowValidationExceptionWhenUserNotFoundByEmail() {
        String email = "pablo.casas@yahoo.com.co";
        String password = "owner123";
        Mockito.when(userPersistencePort.getUserByEmail(email)).thenReturn(null);
        InvalidCredentialsException exception = assertThrows(InvalidCredentialsException.class, () -> {
            authenticationUseCase.authenticationUser(email, password);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.INVALID_CREDENTIALS_MESSAGE);
        Mockito.verify(userPersistencePort, Mockito.times(1)).getUserByEmail(email);
        Mockito.verifyNoMoreInteractions(encoderPort, tokenProviderPort);
    }

    @Test
    @DisplayName("Validation exception when invalid password")
    void authenticationUserShouldThrowValidationExceptionWhenInvalidPassword() {
        User user = new User(10L, "Pablo", "Casas", "99123456789",
                "+573233039679", LocalDate.parse("1990-05-15"), "pablo.casas@yahoo.com.co",
                "$2a$10$Mh8tJKUQ7RusWMkVXIpXb.PfuxMwGEDCkhIeXTKIGhmL9l/XsJemW", new Role(2L, null, null));
        String email = "pablo.casas@yahoo.com.co";
        String password = "owner123";
        Mockito.when(userPersistencePort.getUserByEmail(email)).thenReturn(user);
        Mockito.when(encoderPort.matches(password, user.getPassword())).thenReturn(false);
        InvalidCredentialsException exception = assertThrows(InvalidCredentialsException.class, () -> {
            authenticationUseCase.authenticationUser(email, password);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.INVALID_CREDENTIALS_MESSAGE);
        Mockito.verify(userPersistencePort, Mockito.times(1)).getUserByEmail(email);
        Mockito.verify(encoderPort, Mockito.times(1)).matches(password, user.getPassword());
        Mockito.verifyNoMoreInteractions(tokenProviderPort);
    }
}