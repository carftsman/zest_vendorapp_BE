package com.dhatvibs.modules.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

//    private final String SECRET = "your-secret-key";

    private final long ACCESS_TOKEN_VALIDITY = 1000 * 60 * 15; // 15 min
    private final long REFRESH_TOKEN_VALIDITY = 1000 * 60 * 60 * 24 * 7; // 7 days

    public String generateAccessToken(String ownerId , String restaurantId) {
        return Jwts.builder()
                .setSubject(ownerId)
                .claim("restaurantId", restaurantId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
               // .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256))

                .compact();
    }

    public String generateRefreshToken(String ownerId, String restaurantId) {
        return Jwts.builder()
                .setSubject(ownerId)
                .claim("restaurantId", restaurantId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY))
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256))
                .compact();
    }

    public String extractPhone(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.secretKeyFor(SignatureAlgorithm.HS256))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}