package com.goormthon.bookduchilseong.global.oauth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record KakaoLoginResponseDto(
        @NonNull Long accountId,
        @NonNull String accessToken,
        @NonNull String refreshToken,
        @Schema(description = "신규 유저 여부.")
        boolean isNewUser
) {

}