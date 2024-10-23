package com.pragma.users_microservice.application.dto.request;

import com.pragma.users_microservice.application.constants.RequestConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginRequest {
    @NotBlank(message = RequestConstants.EMAIL_MANDATORY_MESSAGE)
    @Pattern(regexp = RequestConstants.EMAIL_REGULAR_EXPRESSION, message = RequestConstants.INVALID_EMAIL_STRUCTURE_MESSAGE)
    private String email;

    @NotBlank(message = RequestConstants.PASSWORD_MANDATORY_MESSAGE)
    private String password;
}
