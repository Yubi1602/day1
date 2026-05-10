package com.yo.day1.security;


import com.yo.day1.config.AppJwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    private final AppJwtProperties jwtProperties;
    private final SecretKey key;

    public JwtTokenProvider(AppJwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.key = Keys.hmacShaKeyFor(jwtProperties.secret().getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username, Long userId, String fullName, List<String> roles, Long parentId, Long teacherId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtProperties.accessTokenTtlMinutes() * 60 * 1000L);

        var builder = Jwts.builder()
                .subject(username)
                .claim("userId", userId)
                .claim("fullName", fullName)
                .claim("roles", roles);

        if (parentId != null) {
            builder.claim("parentId", parentId);
        }
        if (teacherId != null) {
            builder.claim("teacherId", teacherId);
        }

        return builder
                .issuer(jwtProperties.issuer())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (Exception ex) {
            // Invalid token
            return false;
        }
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
