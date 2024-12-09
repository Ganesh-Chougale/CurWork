package com.PB.ParkingBay.JWT;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {
	
	private final String SK = "com.PB.ParkingBay";
	
	// Generate JWT Token
	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.signWith(SignatureAlgorithm.HS256, SK)
				.compact();	
	}
	
	// Validate Token
	public boolean validateToken(String token, String username) {
		final String extractUsername = extractUsername(token);
		return extractUsername.equals(username) && !isTokenExpired(token);
	}
	
	// Extract Username from Token
	public String extractUsername(String token) {
		return extractClaims(token).getSubject();
	}
	
	// Check Token Expiry
	public boolean isTokenExpired(String token) {
		return extractClaims(token).getExpiration().before(new Date());
	}
	
	private Claims extractClaims(String token) {
		return Jwts.parser()
				.setSigningKey(SK)
				.parseClaimsJws(token)
				.getBody();
	}
	
}
