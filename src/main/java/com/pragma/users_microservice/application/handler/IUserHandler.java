package com.pragma.users_microservice.application.handler;

import com.pragma.users_microservice.application.dto.request.RegisterUserRequest;

public interface IUserHandler {
    void createUser(RegisterUserRequest registerUserRequest);
}
