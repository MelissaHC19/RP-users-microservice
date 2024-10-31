package com.pragma.users_microservice.infrastructure.output.jpa.repository;

import com.pragma.users_microservice.infrastructure.output.jpa.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IEmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    Optional<EmployeeEntity> findByUserId(Long employeeId);
}
