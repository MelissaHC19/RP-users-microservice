package com.pragma.users_microservice.application.mapper;

import com.pragma.users_microservice.application.dto.response.GetUserResponse;
import com.pragma.users_microservice.domain.model.Role;
import com.pragma.users_microservice.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface IGetUserResponseMapper {
    @Mapping(source = "role", target = "role", qualifiedByName = "mapRoleToRole")
    GetUserResponse userToResponse(User user);

    @Named("mapRoleToRole")
    default String mapRoleToRole(Role role) {
        return role.getName();
    }
}