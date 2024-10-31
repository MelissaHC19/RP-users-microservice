package com.pragma.users_microservice.infrastructure.security.adapter;

import com.pragma.users_microservice.domain.spi.IAuthenticationPort;
import com.pragma.users_microservice.infrastructure.security.service.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationAdapter implements IAuthenticationPort {
    @Override
    public Long getUserId() {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Long.valueOf(customUserDetails.getUserId());
    }
}
