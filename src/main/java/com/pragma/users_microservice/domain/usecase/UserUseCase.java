package com.pragma.users_microservice.domain.usecase;

import com.pragma.users_microservice.domain.api.IUserServicePort;
import com.pragma.users_microservice.domain.constants.ExceptionConstants;
import com.pragma.users_microservice.domain.constants.UseCaseConstants;
import com.pragma.users_microservice.domain.exception.*;
import com.pragma.users_microservice.domain.model.User;
import com.pragma.users_microservice.domain.spi.IPasswordEncoderPort;
import com.pragma.users_microservice.domain.spi.IUserPersistencePort;

import java.time.LocalDate;

public class UserUseCase implements IUserServicePort {
    private final IUserPersistencePort userPersistencePort;
    private final IPasswordEncoderPort passwordEncoderPort;

    public UserUseCase(IUserPersistencePort userPersistencePort, IPasswordEncoderPort passwordEncoderPort) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public void createUser(User user) {
        validateUser(user);
        user.setPassword(passwordEncoderPort.passwordEncoder(user.getPassword()));
        userPersistencePort.createUser(user);
    }

    @Override
    public User getUserById(Long id) {
        return userPersistencePort.getUserById(id);
    }

    private void validateUser(User user) {
        if (user.getBirthdate().isAfter(LocalDate.now().minusYears(UseCaseConstants.LEGAL_AGE))) {
            throw new UnderageUserException(ExceptionConstants.UNDERAGE_USER_MESSAGE);
        }
        if (userPersistencePort.alreadyExistsByIdentityDocument(user.getIdentityDocument())) {
            throw new AlreadyExistsByIdentityDocumentException(ExceptionConstants.ALREADY_EXISTS_BY_IDENTITY_DOCUMENT_MESSAGE);
        }
        if (userPersistencePort.alreadyExistsByEmail(user.getEmail())) {
            throw new AlreadyExistsByEmailException(ExceptionConstants.ALREADY_EXISTS_BY_EMAIL_MESSAGE);
        }
    }
}