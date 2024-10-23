package com.pragma.users_microservice.domain.spi;

import java.time.LocalDateTime;
import java.util.Map;

public interface ITokenProviderPort {
    String generateToken(LocalDateTime issuedAt, String subject, LocalDateTime expiresAt, Map<String, Object> claims);
    Object extractClaim(String token, String claimKey);
    String extractSubject(String token);
    Map<String, Object> extractAllClaims(String token);
    Boolean validateToken(String token, String subject);
    Boolean isTokenExpired(String token);
    String extractRole(String token);
}