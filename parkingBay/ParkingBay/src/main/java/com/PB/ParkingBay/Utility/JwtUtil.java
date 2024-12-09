package com.PB.ParkingBay.Utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {
	
    public Key SecretKeyGenerator() {
        Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        System.out.println("Generated Key: " + secretKey); // You can log the key if needed
        return secretKey;
    }

    private Key SECRET_KEY = SecretKeyGenerator(); // Call the method to get the key


//    private String SECRET_KEY = "dnecabtoobgnirpsyabgnikrap"; // Use a stronger key in production
    private long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour expiration

    // Generate JWT Token
    public String generateToken(String username, Map<String, Object> claims) {
        if (claims == null) {
            claims = Map.of();  // Ensure claims is not null
        }

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(Date.from(Instant.now())) // Using Instant.now()
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Validate JWT Token
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token); // Parsing the JWT
            return true; // Token is valid
        } catch (Exception e) {
            // Logging the error or rethrowing could be beneficial here
            return false; // Token is invalid
        }
    }

    // Extract Username from Token
    public String extractUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject(); // Extracting the subject (username)
    }
}
