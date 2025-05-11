package com.foodwaste.config;

import com.foodwaste.entity.User;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "foodWasteSecretKey";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;

    @SuppressWarnings("deprecation")
	public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    public boolean validateToken(String token, User user) {
        return extractEmail(token).equals(user.getEmail()) && !getClaims(token).getExpiration().before(new Date());
    }

    @SuppressWarnings("deprecation")
	private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
