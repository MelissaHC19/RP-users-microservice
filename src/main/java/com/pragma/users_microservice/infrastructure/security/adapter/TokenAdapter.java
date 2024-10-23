package com.pragma.users_microservice.infrastructure.security.adapter;

import com.pragma.users_microservice.domain.spi.ITokenProviderPort;
import com.pragma.users_microservice.infrastructure.constants.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

public class TokenAdapter implements ITokenProviderPort {
    @Override
    public String generateToken(LocalDateTime issuedAt, String subject, LocalDateTime expiresAt, Map<String, Object> claims) {
        return createToken(claims, subject, issuedAt, expiresAt);
    }

    private String createToken(Map<String, Object> claims, String subject, LocalDateTime issuedAt, LocalDateTime expiresAt) {
        Date issuedAtToDate = Date.from(issuedAt.atZone(ZoneId.systemDefault()).toInstant());
        Date expiresAtToDate = Date.from(expiresAt.atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(issuedAtToDate)
                .expiration(expiresAtToDate)
                .signWith(getKey())
                .compact();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SecurityConstants.SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public Object extractClaim(String token, String claimKey) {
        Map<String, Object> claims = extractAllClaims(token);
        return claims.get(claimKey);
    }

    @Override
    public String extractSubject(String token) {
        return (String) extractClaim(token, SecurityConstants.CLAIMS_SUB);
    }

    @Override
    public Map<String, Object> extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    }

    @Override
    public Boolean validateToken(String token, String subject) {
        final String tokenUserId = (String) extractClaim(token, SecurityConstants.CLAIMS_SUB);
        return (subject.equals(tokenUserId) && !isTokenExpired(token));
    }

    @Override
    public Boolean isTokenExpired(String token) {
        Long expirationTimestamp = (Long) extractClaim(token, SecurityConstants.CLAIMS_EXP);
        Date expirationDate = new Date(expirationTimestamp * SecurityConstants.EXPIRES_AT);
        return expirationDate.before(new Date());
    }

    @Override
    public String extractRole(String token) {
        return (String) extractAllClaims(token).get(SecurityConstants.CLAIMS_ROLE);
    }
}
