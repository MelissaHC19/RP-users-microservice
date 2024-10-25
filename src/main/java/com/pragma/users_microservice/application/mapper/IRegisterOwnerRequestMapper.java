package com.pragma.users_microservice.application.mapper;

import com.pragma.users_microservice.application.dto.request.RegisterOwnerRequest;
import com.pragma.users_microservice.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IRegisterOwnerRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    User requestToUser(RegisterOwnerRequest registerOwnerRequest);
}
