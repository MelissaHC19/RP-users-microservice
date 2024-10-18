package com.pragma.users_microservice.application.mapper;

import com.pragma.users_microservice.application.dto.request.RegisterUserRequest;
import com.pragma.users_microservice.domain.model.Role;
import com.pragma.users_microservice.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface IRegisterUserRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    User requestToUser(RegisterUserRequest registerUserRequest);
}
