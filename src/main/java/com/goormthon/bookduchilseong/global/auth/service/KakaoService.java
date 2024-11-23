package com.goormthon.bookduchilseong.global.auth.service;

import com.goormthon.bookduchilseong.global.auth.dto.response.KakaoUserResponseDto;

public interface KakaoService {
    // 카카오 사용자 정보를 가져오기
    KakaoUserResponseDto getKakaoUser(String accessToken);
}
