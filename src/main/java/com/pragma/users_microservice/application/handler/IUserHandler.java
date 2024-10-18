package com.pragma.users_microservice.application.handler;

import com.pragma.users_microservice.application.dto.request.RegisterUserRequest;
import com.pragma.users_microservice.application.dto.response.GetUserResponse;
import com.pragma.users_microservice.domain.model.User;

public interface IUserHandler {
    void createOwner(RegisterUserRequest registerUserRequest);
    GetUserResponse getOwnerById(Long id);
}