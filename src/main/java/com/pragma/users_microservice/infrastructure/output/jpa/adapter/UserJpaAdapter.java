package com.pragma.users_microservice.infrastructure.output.jpa.adapter;

import com.pragma.users_microservice.domain.model.User;
import com.pragma.users_microservice.domain.spi.IUserPersistencePort;
import com.pragma.users_microservice.infrastructure.constants.ExceptionConstants;
import com.pragma.users_microservice.infrastructure.exception.UserNotFoundException;
import com.pragma.users_microservice.infrastructure.output.jpa.mapper.IUserEntityMapper;
import com.pragma.users_microservice.infrastructure.output.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {
    private final IUserEntityMapper userEntityMapper;
    private final IUserRepository userRepository;

    @Override
    public void createUser(User user) {
        userRepository.save(userEntityMapper.userToEntity(user));
    }

    @Override
    public boolean alreadyExistsByIdentityDocument(String identityDocument) {
        return userRepository.findByIdentityDocument(identityDocument).isPresent();
    }

    @Override
    public boolean alreadyExistsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    
    @Override
    public User getUserById(Long id) {
        return userEntityMapper.entityToUser(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format(ExceptionConstants.USER_NOT_FOUND_MESSAGE, id))));
    }
}