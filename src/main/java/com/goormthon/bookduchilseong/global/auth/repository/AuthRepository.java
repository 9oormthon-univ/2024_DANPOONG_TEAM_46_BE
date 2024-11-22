package com.goormthon.bookduchilseong.global.auth.repository;

import java.util.Optional;

public interface AuthRepository {
    // Refresh Token 저장
    void saveRefreshToken(Long userId, String refreshToken, long expirationTime);

    // Refresh Token 조회
    Optional<String> findRefreshTokenByAccountId(Long userId);

    // Refresh Token 삭제
    void deleteRefreshToken(Long userId);
}
