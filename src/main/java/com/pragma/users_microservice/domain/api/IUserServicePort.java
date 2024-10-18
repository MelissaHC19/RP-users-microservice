package com.pragma.users_microservice.domain.api;

import com.pragma.users_microservice.domain.model.User;

public interface IUserServicePort {
    void createOwner(User user);
    boolean getOwnerById(Long id);
}
