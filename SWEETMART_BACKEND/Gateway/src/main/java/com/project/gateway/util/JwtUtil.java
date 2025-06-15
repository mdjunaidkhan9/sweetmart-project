package com.project.gateway.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    // Secret key should match the one used by auth-service
    private final String secretString = "aVeryLongSecretKeyThatIsAtLeast64BytesLongForHS512AlgorithmUse1234567890!";

    private SecretKey secretKey;

    private final long expirationMs = 86400000; // Token validity: 1 day

    @PostConstruct
    public void init() {
        // Generate secret key from string
        secretKey = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
    }

    // Parse claims from token
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Validate token expiration and integrity
    public boolean validateToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return !claims.getExpiration().before(new Date()); // Check expiration
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Extract username (subject) from token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Extract role claim from token
    public String extractRole(String token) {
        return (String) extractAllClaims(token).get("role");
    }
}
