package com.goormthon.bookduchilseong.global.auth.service;

import com.goormthon.bookduchilseong.global.auth.dto.response.AccountLoginResponseDto;
import com.goormthon.bookduchilseong.global.auth.dto.response.TokenRefreshResponseDto;

public interface AuthService {
    // 로그인 처리
    AccountLoginResponseDto login(String accessToken);
    TokenRefreshResponseDto refreshToken(String refreshToken);
    void logout(Long userId);
}
