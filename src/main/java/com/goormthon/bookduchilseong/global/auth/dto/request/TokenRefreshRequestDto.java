package com.goormthon.bookduchilseong.global.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenRefreshRequestDto {
    private String refreshToken;
}
