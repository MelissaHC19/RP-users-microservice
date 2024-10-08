package com.pragma.users_microservice.application.dto.request;

import com.pragma.users_microservice.application.constants.RequestConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class RegisterUserRequest {
    @NotBlank(message = RequestConstants.NAME_MANDATORY_MESSAGE)
    private String name;

    @NotBlank(message = RequestConstants.LAST_NAME_MANDATORY_MESSAGE)
    private String lastName;

    @NotBlank(message = RequestConstants.IDENTITY_DOCUMENT_MANDATORY_MESSAGE)
    private String identityDocument;

    @NotBlank(message = RequestConstants.PHONE_NUMBER_MANDATORY_MESSAGE)
    private String phoneNumber;

    @NotNull(message = RequestConstants.BIRTHDATE_MANDATORY_MESSAGE)
    private LocalDate birthdate;

    @NotBlank(message = RequestConstants.EMAIL_MANDATORY_MESSAGE)
    private String email;

    @NotBlank(message = RequestConstants.PASSWORD_MANDATORY_MESSAGE)
    private String password;

    @NotNull(message = RequestConstants.ROLE_MANDATORY_MESSAGE)
    private Long roleID;
}
