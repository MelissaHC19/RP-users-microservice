package com.pragma.users_microservice.infrastructure.configuration;

import com.pragma.users_microservice.infrastructure.constants.SecurityConstants;
import com.pragma.users_microservice.infrastructure.security.filter.JwtAuthenticationFilter;
import com.pragma.users_microservice.infrastructure.security.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final BeanConfiguration beanConfiguration;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers(SecurityConstants.CREATE_OWNER_PATH, SecurityConstants.GET_OWNER_BY_ID_PATH).hasRole(SecurityConstants.ROLE_ADMIN);
                    registry.requestMatchers(SecurityConstants.LOGIN_PATH, SecurityConstants.CREATE_CLIENT_PATH).permitAll();
                    registry.requestMatchers(SecurityConstants.SWAGGER_PATH, SecurityConstants.SWAGGER_PATH_2, SecurityConstants.SWAGGER_PATH_3, SecurityConstants.SWAGGER_PATH_4).permitAll();
                    registry.requestMatchers(SecurityConstants.CREATE_EMPLOYEE_PATH).hasRole(SecurityConstants.ROLE_OWNER);
                })
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType(SecurityConstants.CONTENT_TYPE);
                            response.getWriter().write(SecurityConstants.FORBIDDEN_MESSAGE);
                }))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(beanConfiguration.passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }
}
