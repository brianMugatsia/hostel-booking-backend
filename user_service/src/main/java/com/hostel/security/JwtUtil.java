package com.hostel.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

        @Value("${jwt.secret}")
        private String secret;

        private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000;

        // Generate a token for a username
        public String generateToken(String username) {
                Map<String, Object> claims = new HashMap<>();
                return createToken(claims, username);
        }

        // Create JWT token
        private String createToken(Map<String, Object> claims, String subject) {
                Key key = Keys.hmacShaKeyFor(secret.getBytes());
                return Jwts.builder()
                                .setClaims(claims)
                                .setSubject(subject)
                                .setIssuedAt(new Date(System.currentTimeMillis()))
                                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                                .signWith(key, SignatureAlgorithm.HS256)
                                .compact();
        }

        // Extract username from token
        public String extractUsername(String token) {
                return extractClaim(token, Claims::getSubject);
        }

        // Extract expiration date
        public Date extractExpiration(String token) {
                return extractClaim(token, Claims::getExpiration);
        }

        // Generic method to extract a claim
        public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
                final Claims claims = extractAllClaims(token);
                return claimsResolver.apply(claims);
        }

        // Check if token expired
        public Boolean isTokenExpired(String token) {
                return extractExpiration(token).before(new Date());
        }

        // Validate token
        public Boolean validateToken(String token, String username) {
                final String tokenUsername = extractUsername(token);
                return (tokenUsername.equals(username) && !isTokenExpired(token));
        }

        // Make this public so other classes can access claims
        public Claims extractAllClaims(String token) {
                Key key = Keys.hmacShaKeyFor(secret.getBytes());
                return Jwts.parserBuilder()
                                .setSigningKey(key)
                                .build()
                                .parseClaimsJws(token)
                                .getBody();
        }

        // Optional: alias for filters
        public Claims extractClaims(String token) {
                return extractAllClaims(token);
        }
}