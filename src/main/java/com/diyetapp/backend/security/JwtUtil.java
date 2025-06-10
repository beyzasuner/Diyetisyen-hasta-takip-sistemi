package com.diyetapp.backend.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "diyetapp-secret-key"; // güvenli ve rastgele bir şey seç
    private final long EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 30; // 30 gün

    // ✅ Token üretme
    public String generateToken(String tcNo, String role) {
        return Jwts.builder()
                .setSubject(tcNo)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // ✅ Token'dan kullanıcı TC'sini al
    public String extractTcNo(String token) {
        return extractClaims(token).getSubject();
    }

    // ✅ Token'dan rolü al
    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    // ✅ Token geçerli mi?
    public boolean isTokenValid(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // 🔧 İçeride kullanılan yardımcı method
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}