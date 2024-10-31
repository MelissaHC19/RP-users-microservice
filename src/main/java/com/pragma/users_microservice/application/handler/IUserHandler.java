package com.pragma.users_microservice.application.handler;

import com.pragma.users_microservice.application.dto.request.RegisterEmployeeRequest;
import com.pragma.users_microservice.application.dto.request.RegisterUserRequest;
import com.pragma.users_microservice.application.dto.request.RegisterOwnerRequest;
import com.pragma.users_microservice.application.dto.response.GetEmployeesRestaurantResponse;
import com.pragma.users_microservice.application.dto.response.GetUserResponse;

public interface IUserHandler {
    void createOwner(RegisterOwnerRequest registerOwnerRequest);
    GetUserResponse getOwnerById(Long id);
    void createEmployee(RegisterEmployeeRequest registerEmployeeRequest);
    void createClient(RegisterUserRequest registerUserRequest);
    GetEmployeesRestaurantResponse getEmployeesRestaurant(Long employeeId);
}