package com.pragma.users_microservice.infrastructure.output.jpa.mapper;

import com.pragma.users_microservice.domain.model.User;
import com.pragma.users_microservice.infrastructure.output.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IUserEntityMapper {
    @Mapping(source = "role", target = "roleID")
    UserEntity userToEntity(User user);

    @Mapping(source = "roleID", target = "role")
    User entityToUser(UserEntity userEntity);
}