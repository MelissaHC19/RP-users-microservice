package com.pragma.users_microservice.application.handler;

import com.pragma.users_microservice.application.dto.request.RegisterUserRequest;
import com.pragma.users_microservice.application.dto.response.GetUserResponse;
import com.pragma.users_microservice.application.mapper.IRegisterUserRequestMapper;
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
    private final IRegisterUserRequestMapper registerUserRequestMapper;


    @Override
    public void createUser(RegisterUserRequest registerUserRequest) {
        User user = registerUserRequestMapper.requestToUser(registerUserRequest);
        userServicePort.createUser(user);
    }

    @Override
    public GetUserResponse getOwnerById(Long id) {
        return new GetUserResponse(userServicePort.getOwnerById(id));
    }
}