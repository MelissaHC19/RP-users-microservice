package com.pragma.users_microservice.infrastructure.input.rest;

import com.pragma.users_microservice.application.dto.request.LoginRequest;
import com.pragma.users_microservice.application.dto.response.AuthResponse;
import com.pragma.users_microservice.application.handler.IAuthenticationHandler;
import com.pragma.users_microservice.infrastructure.constants.DocumentationConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthRestControllerAdapter {
    private final IAuthenticationHandler authenticationHandler;

    @Operation(summary = DocumentationConstants.LOGIN_SUMMARY,
            tags = {DocumentationConstants.USER_TAG},
            description = DocumentationConstants.LOGIN_DESCRIPTION
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = DocumentationConstants.OK_STATUS_CODE,
                    description = DocumentationConstants.OK_RESPONSE_CODE_DESCRIPTION_LOGIN,
                    content = @Content),
            @ApiResponse(responseCode = DocumentationConstants.UNAUTHORIZED_STATUS_CODE,
                    description = DocumentationConstants.UNAUTHORIZED_RESPONSE_CODE_DESCRIPTION_LOGIN,
                    content = @Content),
            @ApiResponse(responseCode = DocumentationConstants.FORBIDDEN_STATUS_CODE,
                    description = DocumentationConstants.FORBIDDEN_RESPONSE_CODE_DESCRIPTION_LOGIN,
                    content = @Content),
    })
    @PostMapping("/login/user")
    public ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody LoginRequest request) {
        AuthResponse tokenResponse = authenticationHandler.authenticationUser(request);
        return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
    }
}
