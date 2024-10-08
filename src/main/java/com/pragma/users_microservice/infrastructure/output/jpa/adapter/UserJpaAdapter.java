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
    public void createUser(User user) {
        userRepository.save(userEntityMapper.userToEntity(user));
    }
}