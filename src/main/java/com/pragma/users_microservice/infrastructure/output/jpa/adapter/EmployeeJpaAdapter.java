package com.pragma.users_microservice.infrastructure.output.jpa.adapter;

import com.pragma.users_microservice.domain.model.Employee;
import com.pragma.users_microservice.domain.spi.IEmployeePersistencePort;
import com.pragma.users_microservice.infrastructure.output.jpa.mapper.IEmployeeEntityMapper;
import com.pragma.users_microservice.infrastructure.output.jpa.repository.IEmployeeRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmployeeJpaAdapter implements IEmployeePersistencePort {
    private final IEmployeeRepository employeeRepository;
    private final IEmployeeEntityMapper employeeEntityMapper;

    @Override
    public void createEmployee(Employee employee) {
        employeeRepository.save(employeeEntityMapper.userToEntity(employee));
    }

    @Override
    public Employee getEmployeesRestaurant(Long id) {
        return employeeEntityMapper.entityToUser(employeeRepository.findByUserId(id)
                .orElse(null));
    }
}
