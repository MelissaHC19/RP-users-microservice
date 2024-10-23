package com.pragma.users_microservice.infrastructure.security.filter;

import com.pragma.users_microservice.domain.spi.ITokenProviderPort;
import com.pragma.users_microservice.infrastructure.constants.SecurityConstants;
import com.pragma.users_microservice.infrastructure.exceptions.ExpiredTokenException;
import com.pragma.users_microservice.infrastructure.exceptions.InvalidTokenException;
import com.pragma.users_microservice.infrastructure.security.service.CustomUserDetails;
import com.pragma.users_microservice.infrastructure.security.service.CustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final ITokenProviderPort tokenProviderPort;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        String token= null;
        String subject = null;


        if (StringUtils.hasText(authHeader) && authHeader.startsWith(SecurityConstants.BEARER_HEADER)) {
            token = authHeader.substring(SecurityConstants.BEGIN_INDEX);
            try {
                subject = tokenProviderPort.extractSubject(token);
            } catch (ExpiredJwtException e) {
                throw new ExpiredTokenException(SecurityConstants.EXPIRED_TOKEN_MESSAGE);
            } catch (JwtException jwtException) {
                throw new InvalidTokenException(SecurityConstants.INVALID_TOKEN_MESSAGE);
            }
        }

        if (subject != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                CustomUserDetails userDetails = (CustomUserDetails) this.customUserDetailsService.loadUserByUsername(token);
                if (Boolean.TRUE.equals(tokenProviderPort.validateToken(token, subject))) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());

                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    createSecurityContext(usernamePasswordAuthenticationToken);
                }
            } catch (UsernameNotFoundException u) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                throw new UsernameNotFoundException(SecurityConstants.USERNAME_NOT_FOUND_MESSAGE);
            }
        }
        filterChain.doFilter(request, response);
    }

    private static void createSecurityContext(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
