package com.pragma.users_microservice.domain.usecase;

import com.pragma.users_microservice.domain.api.IRestaurantServicePort;
import com.pragma.users_microservice.domain.constants.ExceptionConstants;
import com.pragma.users_microservice.domain.exception.*;
import com.pragma.users_microservice.domain.model.Employee;
import com.pragma.users_microservice.domain.model.Role;
import com.pragma.users_microservice.domain.model.User;
import com.pragma.users_microservice.domain.spi.IAuthenticationPort;
import com.pragma.users_microservice.domain.spi.IEmployeePersistencePort;
import com.pragma.users_microservice.domain.spi.IEncoderPort;
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
    private IEncoderPort passwordEncoderPort;

    @Mock
    private IRestaurantServicePort restaurantServicePort;

    @Mock
    private IAuthenticationPort authenticationPort;

    @Mock
    private IEmployeePersistencePort employeePersistencePort;

    @InjectMocks
    private UserUseCase userUseCase;

    @Test
    @DisplayName("Inserts an owner in the DB")
    void createOwner() {
        User user = new User(1L, "Melissa", "Henao", "1004738846",
                "+573205898802", LocalDate.parse("2001-05-19"), "melissahenao19@gmail.com",
                "owner123", null);
        Long roleOwner = 2L;
        String encodedPassword = "$2a$10$Mh8tJKUQ7RusWMkVXIpXb.PfuxMwGEDCkhIeXTKIGhmL9l/XsJemW";
        Mockito.when(userPersistencePort.alreadyExistsByIdentityDocument("1004738846")).thenReturn(false);
        Mockito.when(userPersistencePort.alreadyExistsByEmail(user.getEmail())).thenReturn(false);
        Mockito.when(passwordEncoderPort.passwordEncoder("owner123")).thenReturn(encodedPassword);
        userUseCase.createOwner(user);
        assertEquals(encodedPassword, user.getPassword());
        assertEquals(roleOwner, user.getRole().getId());
        Mockito.verify(userPersistencePort, Mockito.times(1)).alreadyExistsByIdentityDocument(user.getIdentityDocument());
        Mockito.verify(userPersistencePort, Mockito.times(1)).alreadyExistsByEmail(user.getEmail());
        Mockito.verify(passwordEncoderPort, Mockito.times(1)).passwordEncoder("owner123");
        Mockito.verify(userPersistencePort, Mockito.times(1)).createOwner(user);
    }

    @Test
    @DisplayName("Validation exception when owner doesn't have the legal age to be an owner")
    void createOwnerShouldThrowValidationExceptionWhenUnderageUser() {
        User user = new User(1L, "Melissa", "Henao", "1004738846",
                "+573205898802", LocalDate.parse("2015-05-19"), "melissahenao19@gmail.com",
                "owner123", null);
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
                "owner123", null);
        Mockito.when(userPersistencePort.alreadyExistsByIdentityDocument("1004738846")).thenReturn(true);
        AlreadyExistsByIdentityDocumentException exception = assertThrows(AlreadyExistsByIdentityDocumentException.class, () -> {
            userUseCase.createOwner(user);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.ALREADY_EXISTS_BY_IDENTITY_DOCUMENT_MESSAGE);
        Mockito.verify(userPersistencePort, Mockito.times(1)).alreadyExistsByIdentityDocument(user.getIdentityDocument());
        Mockito.verify(userPersistencePort, Mockito.never()).createOwner(user);
    }

    @Test
    @DisplayName("Validation exception when owner already exists by email in the DB")
    void createOwnerShouldThrowValidationExceptionWhenUserAlreadyExistsByEmail() {
        User user = new User(1L, "Melissa", "Henao", "1004738846",
                "+573205898802", LocalDate.parse("2001-05-19"), "melissahenao19@gmail.com",
                "owner123", null);
        Mockito.when(userPersistencePort.alreadyExistsByIdentityDocument("1004738846")).thenReturn(false);
        Mockito.when(userPersistencePort.alreadyExistsByEmail("melissahenao19@gmail.com")).thenReturn(true);
        AlreadyExistsByEmailException exception = assertThrows(AlreadyExistsByEmailException.class, () -> {
            userUseCase.createOwner(user);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.ALREADY_EXISTS_BY_EMAIL_MESSAGE);
        Mockito.verify(userPersistencePort, Mockito.times(1)).alreadyExistsByIdentityDocument(user.getIdentityDocument());
        Mockito.verify(userPersistencePort, Mockito.times(1)).alreadyExistsByEmail(user.getEmail());
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

    @Test
    @DisplayName("Inserts an employee in the DB")
    void createEmployee() {
        User user = new User(1L, "Manuel", "Casas", "76123456789",
                "+573233039679", null, "manuel.casas@yahoo.com.co",
                "employee123", null);
        Long roleEmployee = 3L;
        Long restaurantId = 1L;
        Long ownerId = 2L;
        String encodedPassword = "$2a$10$Mh8tJKUQ7RusWMkVXIpXb.PfuxMwGEDCkhIeXTKIGhmL9l/XsJemW";
        Mockito.when(userPersistencePort.alreadyExistsByIdentityDocument(user.getIdentityDocument())).thenReturn(false);
        Mockito.when(userPersistencePort.alreadyExistsByEmail(user.getEmail())).thenReturn(false);
        Mockito.when(authenticationPort.getUserId()).thenReturn(ownerId);
        Mockito.when(restaurantServicePort.getRestaurantById(restaurantId, ownerId)).thenReturn(true);
        Mockito.when(passwordEncoderPort.passwordEncoder("employee123")).thenReturn(encodedPassword);
        User userSaved = new User(1L, "Manuel", "Casas", "76123456789",
                "+573233039679", null, "manuel.casas@yahoo.com.co",
                encodedPassword, new Role(roleEmployee, null, null));
        Mockito.when(userPersistencePort.createEmployee(user)).thenReturn(userSaved);
        employeePersistencePort.createEmployee(new Employee(null, userSaved.getId(), restaurantId));
        userUseCase.createEmployee(user, restaurantId);
        assertEquals(encodedPassword, user.getPassword());
        assertEquals(roleEmployee, user.getRole().getId());
        Mockito.verify(userPersistencePort, Mockito.times(1)).alreadyExistsByIdentityDocument(user.getIdentityDocument());
        Mockito.verify(userPersistencePort, Mockito.times(1)).alreadyExistsByEmail(user.getEmail());
        Mockito.verify(authenticationPort, Mockito.times(1)).getUserId();
        Mockito.verify(restaurantServicePort, Mockito.times(1)).getRestaurantById(restaurantId, ownerId);
        Mockito.verify(passwordEncoderPort, Mockito.times(1)).passwordEncoder("employee123");
        Mockito.verify(userPersistencePort, Mockito.times(1)).createEmployee(user);
    }

    @Test
    @DisplayName("Validation exception when employee already exists by identity document in the DB")
    void createEmployeeShouldThrowValidationExceptionWhenUserAlreadyExistsByIdentityDocument() {
        User user = new User(1L, "Manuel", "Casas", "76123456789",
                "+573233039679", null, "manuel.casas@yahoo.com.co",
                "employee123", null);
        Long restaurantId = 1L;
        Mockito.when(userPersistencePort.alreadyExistsByIdentityDocument(user.getIdentityDocument())).thenReturn(true);
        AlreadyExistsByIdentityDocumentException exception = assertThrows(AlreadyExistsByIdentityDocumentException.class, () -> {
            userUseCase.createEmployee(user, restaurantId);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.ALREADY_EXISTS_BY_IDENTITY_DOCUMENT_MESSAGE);
        Mockito.verify(userPersistencePort, Mockito.times(1)).alreadyExistsByIdentityDocument(user.getIdentityDocument());
        Mockito.verify(userPersistencePort, Mockito.never()).createEmployee(user);
    }
//
    @Test
    @DisplayName("Validation exception when employee already exists by email in the DB")
    void createEmployeeShouldThrowValidationExceptionWhenUserAlreadyExistsByEmail() {
        User user = new User(1L, "Manuel", "Casas", "76123456789",
                "+573233039679", null, "manuel.casas@yahoo.com.co",
                "employee123", null);
        Long restaurantId = 1L;
        Mockito.when(userPersistencePort.alreadyExistsByIdentityDocument(user.getIdentityDocument())).thenReturn(false);
        Mockito.when(userPersistencePort.alreadyExistsByEmail(user.getEmail())).thenReturn(true);
        AlreadyExistsByEmailException exception = assertThrows(AlreadyExistsByEmailException.class, () -> {
            userUseCase.createEmployee(user, restaurantId);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.ALREADY_EXISTS_BY_EMAIL_MESSAGE);
        Mockito.verify(userPersistencePort, Mockito.times(1)).alreadyExistsByIdentityDocument(user.getIdentityDocument());
        Mockito.verify(userPersistencePort, Mockito.times(1)).alreadyExistsByEmail(user.getEmail());
        Mockito.verify(userPersistencePort, Mockito.never()).createEmployee(user);
    }

    @Test
    @DisplayName("Validation exception when the restaurant doesn't exist or doesn't belong to the owner creating the employee")
    void createEmployeeShouldThrowValidationExceptionWhenRestaurantDoesNotExistOrDoesNotBelongToOwner() {
        User user = new User(1L, "Manuel", "Casas", "76123456789",
                "+573233039679", null, "manuel.casas@yahoo.com.co",
                "employee123", null);
        Long restaurantId = 1L;
        Long ownerId = 2L;
        Mockito.when(userPersistencePort.alreadyExistsByIdentityDocument(user.getIdentityDocument())).thenReturn(false);
        Mockito.when(userPersistencePort.alreadyExistsByEmail(user.getEmail())).thenReturn(false);
        Mockito.when(authenticationPort.getUserId()).thenReturn(ownerId);
        Mockito.when(restaurantServicePort.getRestaurantById(restaurantId, ownerId)).thenReturn(false);
        RestaurantNotFoundException exception = assertThrows(RestaurantNotFoundException.class, () -> {
            userUseCase.createEmployee(user, restaurantId);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.RESTAURANT_NOT_FOUND_MESSAGE);
        Mockito.verify(userPersistencePort, Mockito.times(1)).alreadyExistsByIdentityDocument(user.getIdentityDocument());
        Mockito.verify(userPersistencePort, Mockito.times(1)).alreadyExistsByEmail(user.getEmail());
        Mockito.verify(authenticationPort, Mockito.times(1)).getUserId();
        Mockito.verify(restaurantServicePort, Mockito.times(1)).getRestaurantById(restaurantId, ownerId);
        Mockito.verify(userPersistencePort, Mockito.never()).createEmployee(user);
    }

    @Test
    @DisplayName("Inserts a client in the DB")
    void createClient() {
        User user = new User(1L, "Carlos", "Jaramillo", "761231456789",
                "+573233038679", null, "carlos.jaramillo@yahoo.com.co",
                "client123", null);
        Long roleOwner = 4L;
        String encodedPassword = "$2a$10$wVi7eQBF4U6fclFqICWqzeS6jIGqhpPfXvcl2UBO8gFcIPMbrOc06";
        Mockito.when(userPersistencePort.alreadyExistsByIdentityDocument(user.getIdentityDocument())).thenReturn(false);
        Mockito.when(userPersistencePort.alreadyExistsByEmail(user.getEmail())).thenReturn(false);
        Mockito.when(passwordEncoderPort.passwordEncoder("client123")).thenReturn(encodedPassword);
        userUseCase.createClient(user);
        assertEquals(encodedPassword, user.getPassword());
        assertEquals(roleOwner, user.getRole().getId());
        Mockito.verify(userPersistencePort, Mockito.times(1)).alreadyExistsByIdentityDocument(user.getIdentityDocument());
        Mockito.verify(userPersistencePort, Mockito.times(1)).alreadyExistsByEmail(user.getEmail());
        Mockito.verify(passwordEncoderPort, Mockito.times(1)).passwordEncoder("client123");
        Mockito.verify(userPersistencePort, Mockito.times(1)).createClient(user);
    }

    @Test
    @DisplayName("Validation exception when client already exists by identity document in the DB")
    void createClientShouldThrowValidationExceptionWhenUserAlreadyExistsByIdentityDocument() {
        User user = new User(1L, "Carlos", "Jaramillo", "761231456789",
                "+573233038679", null, "carlos.jaramillo@yahoo.com.co",
                "client123", null);
        Mockito.when(userPersistencePort.alreadyExistsByIdentityDocument(user.getIdentityDocument())).thenReturn(true);
        AlreadyExistsByIdentityDocumentException exception = assertThrows(AlreadyExistsByIdentityDocumentException.class, () -> {
            userUseCase.createClient(user);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.ALREADY_EXISTS_BY_IDENTITY_DOCUMENT_MESSAGE);
        Mockito.verify(userPersistencePort, Mockito.times(1)).alreadyExistsByIdentityDocument(user.getIdentityDocument());
        Mockito.verify(userPersistencePort, Mockito.never()).createClient(user);
    }

    @Test
    @DisplayName("Validation exception when client already exists by email in the DB")
    void createClientShouldThrowValidationExceptionWhenUserAlreadyExistsByEmail() {
        User user = new User(1L, "Carlos", "Jaramillo", "761231456789",
                "+573233038679", null, "carlos.jaramillo@yahoo.com.co",
                "client123", null);
        Mockito.when(userPersistencePort.alreadyExistsByIdentityDocument(user.getIdentityDocument())).thenReturn(false);
        Mockito.when(userPersistencePort.alreadyExistsByEmail(user.getEmail())).thenReturn(true);
        AlreadyExistsByEmailException exception = assertThrows(AlreadyExistsByEmailException.class, () -> {
            userUseCase.createClient(user);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.ALREADY_EXISTS_BY_EMAIL_MESSAGE);
        Mockito.verify(userPersistencePort, Mockito.times(1)).alreadyExistsByIdentityDocument(user.getIdentityDocument());
        Mockito.verify(userPersistencePort, Mockito.times(1)).alreadyExistsByEmail(user.getEmail());
        Mockito.verify(userPersistencePort, Mockito.never()).createClient(user);
    }

    @Test
    @DisplayName("Returns restaurant id from an employee successfully")
    void getEmployeesRestaurant() {
        Long employeeId = 1L;
        Long restaurantId = 10L;
        Employee employee = new Employee(1L, employeeId, restaurantId);
        Mockito.when(employeePersistencePort.getEmployeesRestaurant(employeeId)).thenReturn(employee);

        Long result = userUseCase.getEmployeesRestaurant(employeeId);

        assertEquals(restaurantId, result);
        Mockito.verify(employeePersistencePort, Mockito.times(1)).getEmployeesRestaurant(employeeId);
    }
}