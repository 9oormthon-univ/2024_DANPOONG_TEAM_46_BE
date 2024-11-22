package com.goormthon.bookduchilseong.global.auth.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class AuthRepositoryImpl implements AuthRepository{
    private final RedisTemplate<String, String> redisTemplate;
    private final String KEY_PREFIX = "refresh_token";

    @Override
    public void saveRefreshToken(Long userId, String refreshToken, long expirationTime) {
        String key = KEY_PREFIX + userId;
        redisTemplate.opsForValue().set(key,refreshToken,expirationTime, TimeUnit.MILLISECONDS);
    }
    @Override
    public Optional<String> findRefreshTokenByAccountId(Long userId){
        String key = KEY_PREFIX + userId;
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }
    @Override
    public void deleteRefreshToken(Long userId){
        String key = KEY_PREFIX + userId;
        redisTemplate.delete(key);
    }
}
