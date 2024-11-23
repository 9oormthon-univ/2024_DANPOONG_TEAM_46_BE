package com.goormthon.bookduchilseong.global.auth;
import com.goormthon.bookduchilseong.global.auth.repository.AuthRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AuthRepositoryImplTest {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    void saveAndFindRefreshToken_Success() {
        // Given
        Long userId = 1L;
        String refreshToken = "test_refresh_token";
        long expirationTime = 60000L; // 60초

        // When
        authRepository.saveRefreshToken(userId, refreshToken, expirationTime);
        Optional<String> retrievedToken = authRepository.findRefreshTokenByAccountId(userId);

        // Then
        assertThat(retrievedToken).isPresent();
        assertThat(retrievedToken.get()).isEqualTo(refreshToken);
    }

    @Test
    void deleteRefreshToken_Success() {
        // Given
        Long userId = 2L;
        String refreshToken = "test_refresh_token_to_delete";
        long expirationTime = 60000L;

        authRepository.saveRefreshToken(userId, refreshToken, expirationTime);

        // When
        authRepository.deleteRefreshToken(userId);
        Optional<String> retrievedToken = authRepository.findRefreshTokenByAccountId(userId);

        // Then
        assertThat(retrievedToken).isNotPresent();
    }
}
