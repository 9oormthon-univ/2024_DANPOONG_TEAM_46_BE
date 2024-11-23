package com.goormthon.bookduchilseong.global.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoUserResponseDto(
        Long id,
        @JsonProperty("connected_at") String connectedAt,
        Properties properties,
        @JsonProperty("kakao_account") KakaoAccount kakaoAccount
) {
    public record Properties(
            String nickname,
            @JsonProperty("profile_image") String profileImage,
            @JsonProperty("thumbnail_image") String thumbnailImage
    ) {}

    public record KakaoAccount(
            @JsonProperty("email_needs_agreement") Boolean emailNeedsAgreement,
            @JsonProperty("is_email_valid") Boolean isEmailValid,
            @JsonProperty("is_email_verified") Boolean isEmailVerified,
            String email,
            @JsonProperty("profile_nickname_needs_agreement") Boolean profileNicknameNeedsAgreement,
            @JsonProperty("profile_image_needs_agreement") Boolean profileImageNeedsAgreement,
            Profile profile
    ) {
        public record Profile(
                String nickname,
                @JsonProperty("thumbnail_image_url") String thumbnailImageUrl,
                @JsonProperty("profile_image_url") String profileImageUrl,
                @JsonProperty("is_default_image") Boolean isDefaultImage,
                @JsonProperty("is_default_nickname") Boolean isDefaultNickname
        ) {}
    }
}
