package com.pragma.users_microservice.application.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GetUserResponse {
    private Long id;
    private String name;
    private String lastName;
    private String identityDocument;
    private String phoneNumber;
    private LocalDate birthdate;
    private String email;
    private String password;
    private String role;
}
