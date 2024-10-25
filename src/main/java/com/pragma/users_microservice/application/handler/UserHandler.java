package com.pragma.users_microservice.application.handler;

import com.pragma.users_microservice.application.dto.request.RegisterUserRequest;
import com.pragma.users_microservice.application.dto.request.RegisterOwnerRequest;
import com.pragma.users_microservice.application.dto.response.GetUserResponse;
import com.pragma.users_microservice.application.mapper.IRegisterUserRequestMapper;
import com.pragma.users_microservice.application.mapper.IRegisterOwnerRequestMapper;
import com.pragma.users_microservice.domain.api.IUserServicePort;
import com.pragma.users_microservice.domain.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserHandler implements IUserHandler {
    private final IUserServicePort userServicePort;
    private final IRegisterOwnerRequestMapper registerOwnerRequestMapper;
    private final IRegisterUserRequestMapper registerUserRequestMapper;


    @Override
    public void createOwner(RegisterOwnerRequest registerOwnerRequest) {
        User user = registerOwnerRequestMapper.requestToUser(registerOwnerRequest);
        userServicePort.createOwner(user);
    }

    @Override
    public GetUserResponse getOwnerById(Long id) {
        return new GetUserResponse(userServicePort.getOwnerById(id));
    }

    @Override
    public void createEmployee(RegisterUserRequest registerUserRequest) {
        User user = registerUserRequestMapper.requestToUser(registerUserRequest);
        userServicePort.createEmployee(user);
    }

    @Override
    public void createClient(RegisterUserRequest registerUserRequest) {
        User user = registerUserRequestMapper.requestToUser(registerUserRequest);
        userServicePort.createClient(user);
    }
}