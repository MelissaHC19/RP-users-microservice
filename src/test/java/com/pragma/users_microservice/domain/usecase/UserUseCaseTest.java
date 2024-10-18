package com.pragma.users_microservice.domain.usecase;

import com.pragma.users_microservice.domain.constants.ExceptionConstants;
import com.pragma.users_microservice.domain.exception.*;
import com.pragma.users_microservice.domain.model.Role;
import com.pragma.users_microservice.domain.model.User;
import com.pragma.users_microservice.domain.spi.IPasswordEncoderPort;
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

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {
    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IPasswordEncoderPort passwordEncoderPort;

    @InjectMocks
    private UserUseCase userUseCase;

    @Test
    @DisplayName("Inserts an owner in the DB")
    void createOwner() {
        User user = new User(1L, "Melissa", "Henao", "1004738846",
                "+573205898802", LocalDate.parse("2001-05-19"), "melissahenao19@gmail.com",
                "owner123", new Role(2L, null, null));
        Mockito.when(userPersistencePort.alreadyExistsByIdentityDocument("1004738846")).thenReturn(false);
        user.setPassword(passwordEncoderPort.passwordEncoder(user.getPassword()));
        userUseCase.createOwner(user);
        Mockito.verify(userPersistencePort, Mockito.times(1)).createOwner(user);
        Mockito.verify(passwordEncoderPort, Mockito.times(1)).passwordEncoder(user.getPassword());
    }

    @Test
    @DisplayName("Validation exception when owner doesn't have the legal age to be an owner")
    void createOwnerShouldThrowValidationExceptionWhenUnderageUser() {
        User user = new User(1L, "Melissa", "Henao", "1004738846",
                "+573205898802", LocalDate.parse("2015-05-19"), "melissahenao19@gmail.com",
                "owner123", new Role(2L, null, null));
        UnderageUserException exception = assertThrows(UnderageUserException.class, () -> {
            userUseCase.createOwner(user);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.UNDERAGE_USER_MESSAGE);
        Mockito.verify(userPersistencePort, Mockito.never()).createOwner(user);
    }

    @Test
    @DisplayName("Validation exception when owner already exists by identity document in the DB")
    void createOwnerShouldThrowValidationExceptionWhenUserAlreadyExistsByIdentityDocument() {
        User user = new User(1L, "Melissa", "Henao", "1004738846",
                "+573205898802", LocalDate.parse("2001-05-19"), "melissahenao19@gmail.com",
                "owner123", new Role(2L, null, null));
        Mockito.when(userPersistencePort.alreadyExistsByIdentityDocument("1004738846")).thenReturn(true);
        AlreadyExistsByIdentityDocumentException exception = assertThrows(AlreadyExistsByIdentityDocumentException.class, () -> {
            userUseCase.createOwner(user);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.ALREADY_EXISTS_BY_IDENTITY_DOCUMENT_MESSAGE);
        Mockito.verify(userPersistencePort, Mockito.never()).createOwner(user);
    }

    @Test
    @DisplayName("Validation exception when owner already exists by email in the DB")
    void createOwnerShouldThrowValidationExceptionWhenUserAlreadyExistsByEmail() {
        User user = new User(1L, "Melissa", "Henao", "1004738846",
                "+573205898802", LocalDate.parse("2001-05-19"), "melissahenao19@gmail.com",
                "owner123", new Role(2L, null, null));
        Mockito.when(userPersistencePort.alreadyExistsByEmail("melissahenao19@gmail.com")).thenReturn(true);
        AlreadyExistsByEmailException exception = assertThrows(AlreadyExistsByEmailException.class, () -> {
            userUseCase.createOwner(user);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.ALREADY_EXISTS_BY_EMAIL_MESSAGE);
        Mockito.verify(userPersistencePort, Mockito.never()).createOwner(user);
    }

    @Test
    @DisplayName("Validation when getting owner by id while creating restaurant, the user exists and it's an owner")
    void getOwnerByIdReturnsTrueWhenUserHasOwnerRole() {
        Long ownerId = 1L;
        User user = new User(1L, "Melissa", "Henao", "1004738846",
                "+573205898802", LocalDate.parse("2001-05-19"), "melissahenao19@gmail.com",
                "owner123", new Role(2L, "Owner", null));
        Mockito.when(userPersistencePort.getOwnerById(ownerId)).thenReturn(user);
        boolean result = userUseCase.getOwnerById(ownerId);
        assertTrue(result);
        Mockito.verify(userPersistencePort, Mockito.times(1)).getOwnerById(ownerId);
    }

    @Test
    @DisplayName("Validation when getting owner by id while creating restaurant, the user exists but isn't an owner")
    void getOwnerByIdReturnsFalseWhenUserDoesNotHaveOwnerRole() {
        Long ownerId = 1L;
        User user = new User(1L, "Melissa", "Henao", "1004738846",
                "+573205898802", LocalDate.parse("2001-05-19"), "melissahenao19@gmail.com",
                "owner123", new Role(1L, "Admin", null));
        Mockito.when(userPersistencePort.getOwnerById(ownerId)).thenReturn(user);
        boolean result = userUseCase.getOwnerById(ownerId);
        assertFalse(result);
        Mockito.verify(userPersistencePort, Mockito.times(1)).getOwnerById(ownerId);
    }

    @Test
    @DisplayName("Validation when getting owner by id while creating restaurant, the user doesn't exists")
    void getOwnerByIdShouldThrowValidationExceptionWhenUserNotFound() {
        Long ownerId = 1L;
        Mockito.when(userPersistencePort.getOwnerById(ownerId)).thenReturn(null);
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userUseCase.getOwnerById(ownerId);
        });
        assertThat(exception.getMessage()).isEqualTo(String.format(ExceptionConstants.USER_NOT_FOUND_MESSAGE, ownerId));
        Mockito.verify(userPersistencePort, Mockito.times(1)).getOwnerById(ownerId);
    }
}