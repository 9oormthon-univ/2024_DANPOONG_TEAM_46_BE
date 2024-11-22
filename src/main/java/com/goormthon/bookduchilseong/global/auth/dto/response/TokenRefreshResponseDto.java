package com.goormthon.bookduchilseong.global.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenRefreshResponseDto {
    private String accessToken;
    private String refreshToken;
}