package com.goormthon.bookduchilseong.global.oauth.service;

import com.goormthon.bookduchilseong.global.oauth.dto.response.KakaoLoginResponseDto;
import com.goormthon.bookduchilseong.global.oauth.service.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService {

    @Value("${kakao.client-id}")
    private String kakaoClientId;

    @Value("${kakao.redirect-uri}")
    private String kakaoRedirectUri;

    @Value("${kakao.authorization-uri}")
    private String kakaoAuthorizationUri;

    @Value("${kakao.token-uri}")
    private String kakaoTokenUri;

    @Value("${kakao.user-info-uri}")
    private String kakaoUserInfoUri;

    private final WebClient webClient;

    @Override
    public String getKakaoAuthorizationUrl() {
        return UriComponentsBuilder.fromUriString(kakaoAuthorizationUri)
                .queryParam("response_type", "code")
                .queryParam("client_id", kakaoClientId)
                .queryParam("redirect_uri", kakaoRedirectUri)
                .queryParam("scope", "profile_nickname,profile_image,account_email")
                .toUriString();
    }

    @Override
    public KakaoLoginResponseDto processKakaoCallback(String code) {
        // 1. 인증 코드로 액세스 토큰 요청
        String kakaoAccessToken = getAccessToken(code);

        // 2. 액세스 토큰으로 사용자 정보 요청
        // 실제 로직 추가 (사용자 정보 처리)
        // TODO: 사용자 정보를 기반으로 로그인 또는 회원가입 로직 처리
        log.info("Received access token: {}", kakaoAccessToken);

        return KakaoLoginResponseDto.builder()
                .accountId(1L) // Mock 데이터
                .userRole("USER") // Mock 데이터
                .accessToken(kakaoAccessToken)
                .refreshToken("mock-refresh-token")
                .isNewUser(false)
                .build();
    }

    private String getAccessToken(String code) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", kakaoClientId);
        formData.add("redirect_uri", kakaoRedirectUri);
        formData.add("code", code);

        return webClient.post()
                .uri(kakaoTokenUri)
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(String.class) // 실제 구현에서는 DTO로 변환 필요
                .block(); // 동기 처리
    }
}
