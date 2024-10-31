package com.pragma.users_microservice.domain.usecase;

import com.pragma.users_microservice.domain.api.IRestaurantServicePort;
import com.pragma.users_microservice.domain.api.IUserServicePort;
import com.pragma.users_microservice.domain.constants.ExceptionConstants;
import com.pragma.users_microservice.domain.constants.UseCaseConstants;
import com.pragma.users_microservice.domain.exception.*;
import com.pragma.users_microservice.domain.model.Employee;
import com.pragma.users_microservice.domain.model.Role;
import com.pragma.users_microservice.domain.model.User;
import com.pragma.users_microservice.domain.spi.IAuthenticationPort;
import com.pragma.users_microservice.domain.spi.IEmployeePersistencePort;
import com.pragma.users_microservice.domain.spi.IEncoderPort;
import com.pragma.users_microservice.domain.spi.IUserPersistencePort;

import java.time.LocalDate;

public class UserUseCase implements IUserServicePort {
    private final IUserPersistencePort userPersistencePort;
    private final IEncoderPort passwordEncoderPort;
    private final IRestaurantServicePort restaurantServicePort;
    private final IAuthenticationPort authenticationPort;
    private final IEmployeePersistencePort employeePersistencePort;

    public UserUseCase(IUserPersistencePort userPersistencePort, IEncoderPort passwordEncoderPort, IRestaurantServicePort restaurantServicePort, IAuthenticationPort authenticationPort, IEmployeePersistencePort employeePersistencePort) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.restaurantServicePort = restaurantServicePort;
        this.authenticationPort = authenticationPort;
        this.employeePersistencePort = employeePersistencePort;
    }

    @Override
    public void createOwner(User user) {
        if (user.getBirthdate().isAfter(LocalDate.now().minusYears(UseCaseConstants.LEGAL_AGE))) {
            throw new UnderageUserException(ExceptionConstants.UNDERAGE_USER_MESSAGE);
        }
        validateUser(user);
        user.setPassword(passwordEncoderPort.passwordEncoder(user.getPassword()));
        user.setRole(new Role(UseCaseConstants.ROLE_ID_OWNER, null, null));
        userPersistencePort.createOwner(user);
    }

    @Override
    public boolean getOwnerById(Long id) {
        User user = userPersistencePort.getOwnerById(id);
        if (user == null) {
            throw new UserNotFoundException(String.format(ExceptionConstants.USER_NOT_FOUND_MESSAGE, id));
        }
        return user.getRole().getName().equals(UseCaseConstants.ROLE_OWNER);
    }

    @Override
    public void createEmployee(User user, Long restaurantId) {
        validateUser(user);
        Long ownerId = authenticationPort.getUserId();
        validateRestaurant(restaurantId, ownerId);
        user.setPassword(passwordEncoderPort.passwordEncoder(user.getPassword()));
        user.setRole(new Role(UseCaseConstants.ROLE_ID_EMPLOYEE, null, null));
        User userSaved = userPersistencePort.createEmployee(user);
        employeePersistencePort.createEmployee(new Employee(null, userSaved.getId(), restaurantId));

    }

    @Override
    public void createClient(User user) {
        validateUser(user);
        user.setPassword(passwordEncoderPort.passwordEncoder(user.getPassword()));
        user.setRole(new Role(UseCaseConstants.ROLE_ID_CLIENT, null, null));
        userPersistencePort.createClient(user);
    }

    @Override
    public Long getEmployeesRestaurant(Long employeeId) {
        Employee employee = employeePersistencePort.getEmployeesRestaurant(employeeId);
        return employee.getRestaurantId();
    }

    private void validateUser(User user) {
        if (userPersistencePort.alreadyExistsByIdentityDocument(user.getIdentityDocument())) {
            throw new AlreadyExistsByIdentityDocumentException(ExceptionConstants.ALREADY_EXISTS_BY_IDENTITY_DOCUMENT_MESSAGE);
        }
        if (userPersistencePort.alreadyExistsByEmail(user.getEmail())) {
            throw new AlreadyExistsByEmailException(ExceptionConstants.ALREADY_EXISTS_BY_EMAIL_MESSAGE);
        }
    }

    private void validateRestaurant(Long restaurantId, Long ownerId) {
        boolean existenceValidation = restaurantServicePort.getRestaurantById(restaurantId, ownerId);

        if (!existenceValidation) {
            throw new RestaurantNotFoundException(ExceptionConstants.RESTAURANT_NOT_FOUND_MESSAGE);
        }
    }
}