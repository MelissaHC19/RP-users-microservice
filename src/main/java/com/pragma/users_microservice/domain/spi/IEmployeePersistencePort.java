package com.pragma.users_microservice.domain.spi;

import com.pragma.users_microservice.domain.model.Employee;

public interface IEmployeePersistencePort {
    void createEmployee(Employee employee);
    Employee getEmployeesRestaurant(Long id);
}
