package com.pragma.users_microservice.domain.usecase;

import com.pragma.users_microservice.domain.api.IUserServicePort;
import com.pragma.users_microservice.domain.constants.ExceptionConstants;
import com.pragma.users_microservice.domain.constants.UseCaseConstants;
import com.pragma.users_microservice.domain.exception.*;
import com.pragma.users_microservice.domain.model.User;
import com.pragma.users_microservice.domain.spi.IUserPersistencePort;

import java.time.LocalDate;

public class UserUseCase implements IUserServicePort {
    private final IUserPersistencePort userPersistencePort;

    public UserUseCase(IUserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public void createUser(User user) {
        validateMandatoryFields(user);
        validateUser(user);
        userPersistencePort.createUser(user);
    }

    @Override
    public User getUserById(Long id) {
        return userPersistencePort.getUserById(id);
    }

    private void validateUser(User user) {
        if (!user.getEmail().matches(UseCaseConstants.EMAIL_REGULAR_EXPRESSION)) {
            throw new InvalidEmailStructureException(ExceptionConstants.INVALID_EMAIL_STRUCTURE_MESSAGE);
        }
        if (!user.getPhoneNumber().matches(UseCaseConstants.PHONE_NUMBER_REGULAR_EXPRESSION)) {
            throw new InvalidPhoneNumberException(ExceptionConstants.INVALID_PHONE_NUMBER_MESSAGE);
        }
        if (!user.getIdentityDocument().matches(UseCaseConstants.IDENTITY_DOCUMENT_REGULAR_EXPRESSION)) {
            throw new InvalidIdentityDocumentException(ExceptionConstants.INVALID_IDENTITY_DOCUMENT_MESSAGE);
        }
        if (user.getBirthdate().isAfter(LocalDate.now().minusYears(UseCaseConstants.LEGAL_AGE))) {
            throw new UnderageUserException(ExceptionConstants.UNDERAGE_USER_MESSAGE);
        }
        if (userPersistencePort.alreadyExistsByIdentityDocument(user.getIdentityDocument())) {
            throw new AlreadyExistsByIdentityDocumentException(ExceptionConstants.ALREADY_EXISTS_BY_IDENTITY_DOCUMENT_MESSAGE);
        }
    }

    private void validateMandatoryFields(User user) {
        if (user.getName().trim().isEmpty()) {
            throw new EmptyOrNullFieldsException(ExceptionConstants.NAME_MANDATORY_MESSAGE);
        }
        if (user.getLastName().trim().isEmpty()) {
            throw new EmptyOrNullFieldsException(ExceptionConstants.LAST_NAME_MANDATORY_MESSAGE);
        }
        if (user.getIdentityDocument().trim().isEmpty()) {
            throw new EmptyOrNullFieldsException(ExceptionConstants.IDENTITY_DOCUMENT_MANDATORY_MESSAGE);
        }
        if (user.getPhoneNumber().trim().isEmpty()) {
            throw new EmptyOrNullFieldsException(ExceptionConstants.PHONE_NUMBER_MANDATORY_MESSAGE);
        }
        if (user.getBirthdate() == null) {
            throw new EmptyOrNullFieldsException(ExceptionConstants.BIRTHDATE_MANDATORY_MESSAGE);
        }
        if (user.getEmail().trim().isEmpty()) {
            throw new EmptyOrNullFieldsException(ExceptionConstants.EMAIL_MANDATORY_MESSAGE);
        }
        if (user.getPassword().trim().isEmpty()) {
            throw new EmptyOrNullFieldsException(ExceptionConstants.PASSWORD_MANDATORY_MESSAGE);
        }
        if (user.getRole() == null) {
            throw new EmptyOrNullFieldsException(ExceptionConstants.ROLE_MANDATORY_MESSAGE);
        }
    }
}