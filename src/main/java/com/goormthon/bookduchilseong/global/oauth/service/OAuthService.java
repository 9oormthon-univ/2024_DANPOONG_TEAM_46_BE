package com.goormthon.bookduchilseong.global.oauth.service;

import com.goormthon.bookduchilseong.global.oauth.dto.response.KakaoLoginResponseDto;

public interface OAuthService {

    /**
     * 카카오 로그인 URL을 생성합니다.
     *
     * @return 카카오 로그인 URL
     */
    String getKakaoAuthorizationUrl();

    /**
     * 카카오 인증 콜백을 처리하여 로그인 또는 회원가입을 수행합니다.
     *
     * @param code 카카오에서 반환된 인증 코드
     * @return KakaoLoginResponseDto 사용자 로그인/회원가입 결과
     */
    KakaoLoginResponseDto processKakaoCallback(String code);
}
