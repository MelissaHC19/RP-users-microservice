package com.pragma.users_microservice.infrastructure.output.jpa.adapter;

import com.pragma.users_microservice.domain.model.User;
import com.pragma.users_microservice.domain.spi.IUserPersistencePort;
import com.pragma.users_microservice.infrastructure.output.jpa.mapper.IUserEntityMapper;
import com.pragma.users_microservice.infrastructure.output.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {
    private final IUserEntityMapper userEntityMapper;
    private final IUserRepository userRepository;

    @Override
    public void createOwner(User user) {
        userRepository.save(userEntityMapper.userToEntity(user));
    }

    @Override
    public boolean alreadyExistsByIdentityDocument(String identityDocument) {
        return userRepository.findByIdentityDocument(identityDocument).isPresent();
    }

    @Override
    public boolean alreadyExistsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
    
    @Override
    public User getOwnerById(Long id) {
        return userEntityMapper.entityToUser(userRepository.findById(id)
                .orElse(null));
    }

    @Override
    public User getUserByEmail(String email) {
        return userEntityMapper.entityToUser(userRepository.findByEmail(email)
                .orElse(null));
    }

    @Override
    public void createEmployee(User user) {
        userRepository.save(userEntityMapper.userToEntity(user));
    }

    @Override
    public void createClient(User user) {
        userRepository.save(userEntityMapper.userToEntity(user));
    }
}