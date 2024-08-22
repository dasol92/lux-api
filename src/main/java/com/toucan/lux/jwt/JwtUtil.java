package com.toucan.lux.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@PropertySource("classpath:jwt.yml")
public class JwtUtil {

    private final SecretKey accessSecret;
    private final SecretKey refreshSecret;

    public JwtUtil(@Value("${access_secret}")String accessSecret, @Value("${refresh_secret}")String refreshSecret){
        this.accessSecret = new SecretKeySpec(accessSecret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.refreshSecret = new SecretKeySpec(refreshSecret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    //토큰 검증(username)
    public String getUsername(String token){
        return Jwts.parser().verifyWith(accessSecret).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    //토큰 검증(role)
    public String getRole(String token){
        return Jwts.parser().verifyWith(accessSecret).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    //토큰 말료 확인
    public Boolean isExpired(String token){
        return Jwts.parser().verifyWith(accessSecret).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    //이 토큰이 refresh 토큰인지 access 토큰인지 구별하는법
    public String getCategory(String token){
        return Jwts.parser().verifyWith(accessSecret).build().parseSignedClaims(token).getPayload().get("category", String.class);
    }

    public String createAccessToken(String username, String role, Long expiredMs){
        return Jwts.builder()
                .subject(username)
                .claim("category", "access")
                .claim("username", username)                                    //유저 이름
                .claim("role", role)                                            //권한
                .issuedAt(new Date(System.currentTimeMillis()))                    //현재시간
                .expiration(new Date(System.currentTimeMillis() + expiredMs))       //만료시간
                .signWith(accessSecret)                                                //암호화키
                .compact();
    }

    public String createRefreshToken(String username, String role, Long expiredMs){
        return Jwts.builder()
                .subject(username)
                .claim("category", "refresh")
                .claim("username", username)                                    //유저 이름
                .claim("role", role)                                            //권한
                .issuedAt(new Date(System.currentTimeMillis()))                    //현재시간
                .expiration(new Date(System.currentTimeMillis() + expiredMs))       //만료시간
                .signWith(refreshSecret)                                                //암호화키
                .compact();
    }

    public SecretKey getAccessSecret() {
        return accessSecret;
    }

    public Claims validateRefreshToken(String token) {

        SecretKey key = Keys.hmacShaKeyFor(refreshSecret.toString().getBytes(StandardCharsets.UTF_8));
        Claims claims;
        try {

            claims = Jwts.parser()
                    .verifyWith(refreshSecret)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        } catch (JwtException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Invalid refresh token");
        }

        return claims;
    }
}
