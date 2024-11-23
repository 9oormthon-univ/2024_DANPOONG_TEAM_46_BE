package com.goormthon.bookduchilseong.global.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountLoginResponseDto {
    private Long accountId;
    private String accessToken;
    private String refreshToken;
}
