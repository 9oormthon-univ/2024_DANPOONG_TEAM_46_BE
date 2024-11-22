package com.goormthon.bookduchilseong.global.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoUserResponseDto {
    private long id;
    private String nickname;
    private String thumbnailImageUrl;
}
