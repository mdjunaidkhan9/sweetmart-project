package com.project.auth.service;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component // Makes JwtUtil a Spring-managed component
public class JwtUtil {

    // 64-byte secret key string (required for HS512)
    private final String secretString = "aVeryLongSecretKeyThatIsAtLeast64BytesLongForHS512AlgorithmUse1234567890!";
    private final SecretKey secretKey = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
    private final long expirationMs = 86400000; // Token valid for 24 hours

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username) // Token subject
                .claim("role", role)  // Custom claim
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs)) // Expiration
                .signWith(secretKey, SignatureAlgorithm.HS512) // Signature algorithm
                .compact(); // Builds the token
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // Retrieves username from token
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token); // Parses and validates
            return true;
        } catch (JwtException e) {
            return false; // Invalid or expired
        }
    }
}
