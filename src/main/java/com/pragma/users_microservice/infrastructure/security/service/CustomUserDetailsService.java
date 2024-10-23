package com.pragma.users_microservice.infrastructure.security.service;

import com.pragma.users_microservice.domain.spi.ITokenProviderPort;
import com.pragma.users_microservice.infrastructure.constants.SecurityConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final ITokenProviderPort tokenProviderPort;

    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        if (tokenProviderPort.extractSubject(token) != null && !tokenProviderPort.extractSubject(token).isEmpty()) {
            Collection<GrantedAuthority> authorities = List.of(
                    new SimpleGrantedAuthority(SecurityConstants.AUTHORITY_ROLE_ + tokenProviderPort.extractRole(token))
            );
            return new CustomUserDetails(tokenProviderPort.extractSubject(token), token, authorities, true);
        } else {
            throw new UsernameNotFoundException(SecurityConstants.USERNAME_NOT_FOUND_MESSAGE);
        }
    }
}
