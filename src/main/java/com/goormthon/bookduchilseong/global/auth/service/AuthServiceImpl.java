package com.goormthon.bookduchilseong.global.auth.service;

import com.goormthon.bookduchilseong.domain.user.entity.User;
import com.goormthon.bookduchilseong.domain.user.service.UserService;
import com.goormthon.bookduchilseong.global.auth.dto.response.AccountLoginResponseDto;
import com.goormthon.bookduchilseong.global.auth.dto.response.KakaoUserResponseDto;
import com.goormthon.bookduchilseong.global.auth.dto.response.TokenRefreshResponseDto;
import com.goormthon.bookduchilseong.global.auth.repository.AuthRepository;
import com.goormthon.bookduchilseong.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final KakaoService kakaoService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthRepository authRepository;

    public TokenRefreshResponseDto refreshToken(String refreshToken){
        // 1. Refresh Token 검증
        jwtTokenProvider.validateToken(refreshToken);

        // 2. 사용자 ID 추출
        Long userId = jwtTokenProvider.getUserIdFromToken(refreshToken);

        // 3. Redis에서 Refresh Token 존재 확인
        String storedToken = authRepository.findRefreshTokenByAccountId(userId)
                .orElseThrow(() ->new RuntimeException("Refresh token not found"));

        if(!storedToken.equals(refreshToken)){
            throw new RuntimeException("Invalid refresh token");
        }

        // 4. 새로운 Access Token 및 Refresh Token 생성
        String newAccessToken = jwtTokenProvider.createAccessToken(userId);
        String newRefreshToken = jwtTokenProvider.createRefreshToken(userId);

        // 5. Redis에 새로운 Refresh Token 저장
        authRepository.saveRefreshToken(userId, newRefreshToken, jwtTokenProvider.getRefreshTokenValidity());

        // 6. 응답 반환
        return TokenRefreshResponseDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    @Override
    public void logout(Long userId) {
        authRepository.deleteRefreshToken(userId);
    }

    // 일반 유저 로그인
    public AccountLoginResponseDto login(String accessToken) {
        log.info("일반 유저 로그인");
        return AccountLoginResponseDto.builder()
                .accountId(1L) // 임의의 사용자 ID
                .accessToken("fakeAccessToken") // 가짜 Access Token
                .refreshToken("fakeRefreshToken") // 가짜 Refresh Token
                .build();
    }


}
