package com.pragma.users_microservice.infrastructure.output.jpa.repository;

import com.pragma.users_microservice.infrastructure.output.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {
}
