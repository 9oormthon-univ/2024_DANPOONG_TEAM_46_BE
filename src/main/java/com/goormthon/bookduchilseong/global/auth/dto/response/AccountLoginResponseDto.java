package com.goormthon.bookduchilseong.global.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountLoginResponseDto {
    private Long accountId;
    private String userRole;
    private String accessToken;
    private String refreshToken;
}
