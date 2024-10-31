package com.pragma.users_microservice.infrastructure.output.jpa.mapper;

import com.pragma.users_microservice.domain.model.Employee;
import com.pragma.users_microservice.infrastructure.output.jpa.entity.EmployeeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IEmployeeEntityMapper {
    EmployeeEntity userToEntity(Employee employee);
    Employee entityToUser(EmployeeEntity employeeEntity);
}
