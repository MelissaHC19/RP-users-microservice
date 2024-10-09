package com.pragma.users_microservice.domain.usecase;

import com.pragma.users_microservice.domain.constants.ExceptionConstants;
import com.pragma.users_microservice.domain.exception.*;
import com.pragma.users_microservice.domain.model.Role;
import com.pragma.users_microservice.domain.model.User;
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

    @InjectMocks
    private UserUseCase userUseCase;

    @Test
    @DisplayName("Inserts an owner in the DB")
    void createUser() {
        User user = new User(1L, "Melissa", "Henao", "1004738846",
                "573205898802", LocalDate.parse("2001-05-19"), "melissahenao19@gmail.com",
                "owner123", new Role(2L, null, null));
        Mockito.when(userPersistencePort.alreadyExistsByIdentityDocument("1004738846")).thenReturn(false);
        userUseCase.createUser(user);
        Mockito.verify(userPersistencePort, Mockito.times(1)).createUser(user);
    }

    @Test
    @DisplayName("Validation exception when name field is empty")
    void createUserShouldThrowValidationExceptionWhenNameIsEmpty() {
        User user = new User(1L, "", "Henao", "1004738846",
                "573205898802", LocalDate.parse("2001-05-19"), "melissahenao19@gmail.com",
                "owner123", new Role(2L, null, null));
        EmptyOrNullFieldsException exception = assertThrows(EmptyOrNullFieldsException.class, () -> {
            userUseCase.createUser(user);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.NAME_MANDATORY_MESSAGE);
        Mockito.verify(userPersistencePort, Mockito.never()).createUser(user);
    }

    @Test
    @DisplayName("Validation exception when last name field is empty")
    void createUserShouldThrowValidationExceptionWhenLastNameIsEmpty() {
        User user = new User(1L, "Melissa", "", "1004738846",
                "573205898802", LocalDate.parse("2001-05-19"), "melissahenao19@gmail.com",
                "owner123", new Role(2L, null, null));
        EmptyOrNullFieldsException exception = assertThrows(EmptyOrNullFieldsException.class, () -> {
            userUseCase.createUser(user);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.LAST_NAME_MANDATORY_MESSAGE);
        Mockito.verify(userPersistencePort, Mockito.never()).createUser(user);
    }

    @Test
    @DisplayName("Validation exception when identity document field is empty")
    void createUserShouldThrowValidationExceptionWhenIdentityDocumentIsEmpty() {
        User user = new User(1L, "Melissa", "Henao", "",
                "573205898802", LocalDate.parse("2001-05-19"), "melissahenao19@gmail.com",
                "owner123", new Role(2L, null, null));
        EmptyOrNullFieldsException exception = assertThrows(EmptyOrNullFieldsException.class, () -> {
            userUseCase.createUser(user);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.IDENTITY_DOCUMENT_MANDATORY_MESSAGE);
        Mockito.verify(userPersistencePort, Mockito.never()).createUser(user);
    }

    @Test
    @DisplayName("Validation exception when phone number field is empty")
    void createUserShouldThrowValidationExceptionWhenPhoneNumberIsEmpty() {
        User user = new User(1L, "Melissa", "Henao", "1004738846",
                "", LocalDate.parse("2001-05-19"), "melissahenao19@gmail.com",
                "owner123", new Role(2L, null, null));
        EmptyOrNullFieldsException exception = assertThrows(EmptyOrNullFieldsException.class, () -> {
            userUseCase.createUser(user);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.PHONE_NUMBER_MANDATORY_MESSAGE);
        Mockito.verify(userPersistencePort, Mockito.never()).createUser(user);
    }

    @Test
    @DisplayName("Validation exception when birthdate field is null")
    void createUserShouldThrowValidationExceptionWhenBirthdateIsNull() {
        User user = new User(1L, "Melissa", "Henao", "1004738846",
                "573205898802", null, "melissahenao19@gmail.com",
                "owner123", new Role(2L, null, null));
        EmptyOrNullFieldsException exception = assertThrows(EmptyOrNullFieldsException.class, () -> {
            userUseCase.createUser(user);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.BIRTHDATE_MANDATORY_MESSAGE);
        Mockito.verify(userPersistencePort, Mockito.never()).createUser(user);
    }

    @Test
    @DisplayName("Validation exception when email field is empty")
    void createUserShouldThrowValidationExceptionWhenEmailIsEmpty() {
        User user = new User(1L, "Melissa", "Henao", "1004738846",
                "573205898802", LocalDate.parse("2001-05-19"), "",
                "owner123", new Role(2L, null, null));
        EmptyOrNullFieldsException exception = assertThrows(EmptyOrNullFieldsException.class, () -> {
            userUseCase.createUser(user);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.EMAIL_MANDATORY_MESSAGE);
        Mockito.verify(userPersistencePort, Mockito.never()).createUser(user);
    }

    @Test
    @DisplayName("Validation exception when password field is empty")
    void createUserShouldThrowValidationExceptionWhenPasswordIsEmpty() {
        User user = new User(1L, "Melissa", "Henao", "1004738846",
                "573205898802", LocalDate.parse("2001-05-19"), "melissahenao19@gmail.com",
                "", new Role(2L, null, null));
        EmptyOrNullFieldsException exception = assertThrows(EmptyOrNullFieldsException.class, () -> {
            userUseCase.createUser(user);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.PASSWORD_MANDATORY_MESSAGE);
        Mockito.verify(userPersistencePort, Mockito.never()).createUser(user);
    }

    @Test
    @DisplayName("Validation exception when role field is null")
    void createUserShouldThrowValidationExceptionWhenRoleIsNull() {
        User user = new User(1L, "Melissa", "Henao", "1004738846",
                "573205898802", LocalDate.parse("2001-05-19"), "melissahenao19@gmail.com",
                "owner123", null);
        EmptyOrNullFieldsException exception = assertThrows(EmptyOrNullFieldsException.class, () -> {
            userUseCase.createUser(user);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.ROLE_MANDATORY_MESSAGE);
        Mockito.verify(userPersistencePort, Mockito.never()).createUser(user);
    }

    @Test
    @DisplayName("Validation exception when email structure is invalid")
    void createUserShouldThrowValidationExceptionWhenEmailStructureIsInvalid() {
        User user = new User(1L, "Melissa", "Henao", "1004738846",
                "573205898802", LocalDate.parse("2001-05-19"), "melissahenao19@gmail",
                "owner123", new Role(2L, null, null));
        InvalidEmailStructureException exception = assertThrows(InvalidEmailStructureException.class, () -> {
            userUseCase.createUser(user);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.INVALID_EMAIL_STRUCTURE_MESSAGE);
        Mockito.verify(userPersistencePort, Mockito.never()).createUser(user);
    }

    @Test
    @DisplayName("Validation exception when phone number is invalid")
    void createUserShouldThrowValidationExceptionWhenPhoneNumberIsInvalid() {
        User user = new User(1L, "Melissa", "Henao", "1004738846",
                "+3205898802", LocalDate.parse("2001-05-19"), "melissahenao19@gmail.com",
                "owner123", new Role(2L, null, null));
        InvalidPhoneNumberException exception = assertThrows(InvalidPhoneNumberException.class, () -> {
            userUseCase.createUser(user);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.INVALID_PHONE_NUMBER_MESSAGE);
        Mockito.verify(userPersistencePort, Mockito.never()).createUser(user);
    }

    @Test
    @DisplayName("Validation exception when identity document isn't numeric only")
    void createUserShouldThrowValidationExceptionWhenIdentityDocumentIsInvalid() {
        User user = new User(1L, "Melissa", "Henao", "1.004.738.846",
                "573205898802", LocalDate.parse("2001-05-19"), "melissahenao19@gmail.com",
                "owner123", new Role(2L, null, null));
        InvalidIdentityDocumentException exception = assertThrows(InvalidIdentityDocumentException.class, () -> {
            userUseCase.createUser(user);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.INVALID_IDENTITY_DOCUMENT_MESSAGE);
        Mockito.verify(userPersistencePort, Mockito.never()).createUser(user);
    }

    @Test
    @DisplayName("Validation exception when user doesn't have the legal age to be an owner")
    void createUserShouldThrowValidationExceptionWhenUnderageUser() {
        User user = new User(1L, "Melissa", "Henao", "1004738846",
                "573205898802", LocalDate.parse("2015-05-19"), "melissahenao19@gmail.com",
                "owner123", new Role(2L, null, null));
        UnderageUserException exception = assertThrows(UnderageUserException.class, () -> {
            userUseCase.createUser(user);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.UNDERAGE_USER_MESSAGE);
        Mockito.verify(userPersistencePort, Mockito.never()).createUser(user);
    }

    @Test
    @DisplayName("Inserts an owner in the DB")
    void createUserShouldThrowValidationExceptionWhenUserAlreadyExists() {
        User user = new User(1L, "Melissa", "Henao", "1004738846",
                "573205898802", LocalDate.parse("2001-05-19"), "melissahenao19@gmail.com",
                "owner123", new Role(2L, null, null));
        Mockito.when(userPersistencePort.alreadyExistsByIdentityDocument("1004738846")).thenReturn(true);
        AlreadyExistsByIdentityDocumentException exception = assertThrows(AlreadyExistsByIdentityDocumentException.class, () -> {
            userUseCase.createUser(user);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.ALREADY_EXISTS_BY_IDENTITY_DOCUMENT_MESSAGE);
        Mockito.verify(userPersistencePort, Mockito.never()).createUser(user);
    }
}