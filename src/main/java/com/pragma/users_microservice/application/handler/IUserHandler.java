package com.pragma.users_microservice.application.handler;

import com.pragma.users_microservice.application.dto.request.RegisterUserRequest;
import com.pragma.users_microservice.application.dto.response.GetUserResponse;

public interface IUserHandler {
    void createOwner(RegisterUserRequest registerUserRequest);
    GetUserResponse getOwnerById(Long id);
}