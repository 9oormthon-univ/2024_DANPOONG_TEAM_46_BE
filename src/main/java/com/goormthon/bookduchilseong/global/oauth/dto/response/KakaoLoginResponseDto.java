package com.goormthon.bookduchilseong.global.oauth.dto.response;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record KakaoLoginResponseDto(
	@NonNull String accessToken,
	@NonNull String refreshToken
) {

}