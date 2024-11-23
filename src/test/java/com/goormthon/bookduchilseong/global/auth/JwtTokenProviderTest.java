package com.goormthon.bookduchilseong.global.auth;

import com.goormthon.bookduchilseong.global.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private final String secretKey = "bookduchilsungforbookduchilsungforbookduchilsung";
    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

    @Test
    void testGetUserIdFromToken() {
        // 1. 테스트용 JWT 토큰 생성
        String token = Jwts.builder()
                .setSubject("TestUser")
                .claim("userId", 123L) // 여기에 userId 넣음
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000L)) // 30분 유효
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();

        // 2. 메서드 실행
        Long userId = jwtTokenProvider.getUserIdFromToken(token);

        // 3. 결과 검증
        assertEquals(123L, userId); // 기대값과 비교
    }
}
