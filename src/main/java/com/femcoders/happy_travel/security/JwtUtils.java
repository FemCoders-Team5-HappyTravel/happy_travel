package com.femcoders.happy_travel.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

    @Component
    public class JwtUtils {

        @Value("${jwt.secret}")
        private String secret;

        @Value("${jwt.expiration}")
        private long expiration;

        public String generateToken(UserDetails userDetails) {
            return Jwts.builder()
                    .setSubject(userDetails.getUsername())
                    .claim("roles", userDetails.getAuthorities())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration))
                    .signWith(SignatureAlgorithm.HS256, secret)
                    .compact();
        }

        public boolean isTokenValid(String token, UserDetails userDetails) {
            String username = extractUsername(token);
            return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        }

        public String extractUsername(String token) {
            return extractClaims(token).getSubject();
        }

        public boolean isTokenExpired(String token) {
            return extractClaims(token).getExpiration().before(new Date());
        }

        private Claims extractClaims(String token) {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }
    }

