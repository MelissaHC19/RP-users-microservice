package com.pragma.users_microservice.domain.spi;

import com.pragma.users_microservice.domain.model.User;

public interface IUserPersistencePort {
    void createOwner(User user);
    boolean alreadyExistsByIdentityDocument(String identityDocument);
    boolean alreadyExistsByEmail(String email);
    User getOwnerById(Long id);
    User getUserByEmail(String email);
    void createEmployee(User user);
    void  createClient(User user);
}
