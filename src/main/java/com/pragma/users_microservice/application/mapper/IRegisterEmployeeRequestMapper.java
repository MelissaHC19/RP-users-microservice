package com.pragma.users_microservice.application.mapper;

import com.pragma.users_microservice.application.dto.request.RegisterEmployeeRequest;
import com.pragma.users_microservice.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IRegisterEmployeeRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "birthdate", ignore = true)
    User requestToUser(RegisterEmployeeRequest registerEmployeeRequest);
}
