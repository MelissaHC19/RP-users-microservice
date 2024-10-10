package com.pragma.users_microservice.infrastructure.input.rest;

import com.pragma.users_microservice.application.dto.request.RegisterUserRequest;
import com.pragma.users_microservice.application.dto.response.GetUserResponse;
import com.pragma.users_microservice.application.handler.IUserHandler;
import com.pragma.users_microservice.infrastructure.constants.ControllerConstants;
import com.pragma.users_microservice.infrastructure.constants.DocumentationConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRestControllerAdapter {
    private final IUserHandler userHandler;

    @Operation(summary = DocumentationConstants.CREATE_USER_SUMMARY,
            tags = {DocumentationConstants.USER_TAG},
            description = DocumentationConstants.CREATE_USER_DESCRIPTION
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = DocumentationConstants.CREATED_STATUS_CODE,
                    description = DocumentationConstants.CREATED_RESPONSE_CODE_DESCRIPTION,
                    content = @Content),
            @ApiResponse(responseCode = DocumentationConstants.BAD_REQUEST_STATUS_CODE,
                    description = DocumentationConstants.BAD_REQUEST_RESPONSE_CODE_DESCRIPTION,
                    content = @Content),
            @ApiResponse(responseCode = DocumentationConstants.CONFLICT_STATUS_CODE,
                    description = DocumentationConstants.CONFLICT_RESPONSE_CODE_DESCRIPTION,
                    content = @Content),
    })
    @PostMapping("/create")
    public ResponseEntity<ControllerResponse> createUser(@Valid @RequestBody RegisterUserRequest request) {
        userHandler.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ControllerResponse(ControllerConstants.USER_CREATED_MESSAGE, HttpStatus.CREATED.toString(), LocalDateTime.now()));
    }

    @Operation(summary = DocumentationConstants.GET_USER_SUMMARY,
            tags = {DocumentationConstants.USER_TAG},
            description = DocumentationConstants.GET_USER_DESCRIPTION
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = DocumentationConstants.OK_STATUS_CODE,
                    description = DocumentationConstants.OK_RESPONSE_CODE_DESCRIPTION,
                    content = @Content),
            @ApiResponse(responseCode = DocumentationConstants.NOT_FOUND_STATUS_CODE,
                    description = DocumentationConstants.NOT_FOUND_RESPONSE_CODE_DESCRIPTION,
                    content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userHandler.getUserById(id));
    }
}