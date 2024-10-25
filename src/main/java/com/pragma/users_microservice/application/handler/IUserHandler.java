package com.pragma.users_microservice.application.handler;

import com.pragma.users_microservice.application.dto.request.RegisterUserRequest;
import com.pragma.users_microservice.application.dto.request.RegisterOwnerRequest;
import com.pragma.users_microservice.application.dto.response.GetUserResponse;

public interface IUserHandler {
    void createOwner(RegisterOwnerRequest registerOwnerRequest);
    GetUserResponse getOwnerById(Long id);
    void createEmployee(RegisterUserRequest registerUserRequest);
    void createClient(RegisterUserRequest registerUserRequest);
}