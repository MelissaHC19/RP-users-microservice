package com.pragma.users_microservice.application.dto.request;

import com.pragma.users_microservice.application.constants.RequestConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = RequestConstants.IDENTITY_DOCUMENT_REGULAR_EXPRESSION, message = RequestConstants.INVALID_IDENTITY_DOCUMENT_MESSAGE)
    private String identityDocument;

    @NotBlank(message = RequestConstants.PHONE_NUMBER_MANDATORY_MESSAGE)
    @Pattern(regexp = RequestConstants.PHONE_NUMBER_REGULAR_EXPRESSION, message = RequestConstants.INVALID_PHONE_NUMBER_MESSAGE)
    private String phoneNumber;

    @NotNull(message = RequestConstants.BIRTHDATE_MANDATORY_MESSAGE)
    @Past(message = RequestConstants.BIRTHDATE_FUTURE_MESSAGE)
    private LocalDate birthdate;

    @NotBlank(message = RequestConstants.EMAIL_MANDATORY_MESSAGE)
    @Pattern(regexp = RequestConstants.EMAIL_REGULAR_EXPRESSION, message = RequestConstants.INVALID_EMAIL_STRUCTURE_MESSAGE)
    private String email;

    @NotBlank(message = RequestConstants.PASSWORD_MANDATORY_MESSAGE)
    private String password;
}
