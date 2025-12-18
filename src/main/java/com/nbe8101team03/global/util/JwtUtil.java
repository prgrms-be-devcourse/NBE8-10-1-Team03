package com.nbe8101team03.global.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    // 더 안전한 환경 변수 secret key
//    private static final String SECRET_KEY = System.getenv("JWT_SECRET_KEY");
    @Value("${jwt.secret-key}")
    private String secretKey;

    private SecretKey key;

    @PostConstruct
    private void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    private static final long EXPIRATION_TIME = 1000L * 60 * 30; // 30분 후 expired

    //    JWT 생성
    public String generateToken(String userId, String role) {
        return Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .claim("userId", userId)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    //    token에서 Claims 추출
    public Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // userId 추출
    public String getUserId(String token) {
        return parseClaims(token).get("userId", String.class);
    }

    // role 추출
    public String getRole(String token) {
        return parseClaims(token).get("role", String.class);
    }

    // 토큰 만료 여부
    public boolean isExpired(String token) {
        Date expiration = parseClaims(token).getExpiration();
        return expiration.before(new Date());
    }
}