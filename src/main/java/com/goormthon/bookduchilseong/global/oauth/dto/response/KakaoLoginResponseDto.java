package com.goormthon.bookduchilseong.global.oauth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record KakaoLoginResponseDto(
        @NonNull String accessToken,
        @NonNull String refreshToken
) {

}