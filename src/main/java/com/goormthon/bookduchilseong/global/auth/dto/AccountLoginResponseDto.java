package com.goormthon.bookduchilseong.global.auth.dto;

import lombok.Builder;

@Builder
public record AccountLoginResponseDto(
        Long accountId,
        String userRole,
        String accessToken,
        String refreshToken
) {}