package com.pragma.users_microservice.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetClientsPhoneNumberResponse {
    private String phoneNumber;
}
