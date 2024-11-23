package com.goormthon.bookduchilseong.global.security.jwt;

//import com.goormthon.bookduchilseong.domain.account.entity.Account;
import com.goormthon.bookduchilseong.domain.user.entity.User;
import com.goormthon.bookduchilseong.global.security.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Value("${spring.jwt.secret}")
    private String secretKey;

    private final long ACCESS_TOKEN_VALIDITY = 30 * 60 * 1000L; // 30분
    private final long REFRESH_TOKEN_VALIDITY = 7 * 24 * 60 * 60 * 1000L; // 7일

    // 토큰 유효성 검증
    public void validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes())
                    .build()
                    .parseClaimsJws(token);
        } catch (Exception e) {
            throw new RuntimeException("Invalid token", e);
        }
    }

    // 토큰으로부터 사용자 ID 추출
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("userId", Long.class);
    }

    // Getter for REFRESH_TOKEN_VALIDITY
    public long getRefreshTokenValidity() {
        return REFRESH_TOKEN_VALIDITY;
    }

    public String createAccessToken(Long userId) {
        Claims claims = Jwts.claims().setSubject(userId.toString());
        claims.put("userid", userId);
        return buildToken(claims, ACCESS_TOKEN_VALIDITY);
    }

    public String createRefreshToken(Long userId) {
        Claims claims = Jwts.claims().setSubject(userId.toString());
        return buildToken(claims, REFRESH_TOKEN_VALIDITY);
    }

    private String buildToken(Claims claims, long validity) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + validity);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }
    // 토큰에서 Claims 추출
    public Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }
    // 클레임에서 Authentication 객체 생성
    public Authentication getAuthenticationFromClaims(Claims claims) {
        Long userId = claims.get("userId", Long.class);
        String role = claims.get("role", String.class);

        return new UsernamePasswordAuthenticationToken(
                userId,
                null,
                Collections.singletonList(new SimpleGrantedAuthority(role))
        );
    }
}
