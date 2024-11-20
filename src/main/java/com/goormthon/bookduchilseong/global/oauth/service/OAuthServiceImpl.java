package com.goormthon.bookduchilseong.global.oauth.service;

import com.goormthon.bookduchilseong.global.oauth.dto.kakao.KakaoTokenResponse;
import com.goormthon.bookduchilseong.global.oauth.dto.kakao.KakaoUserInfo;
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

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;

    @Value("${spring.security.oauth2.client.provider.kakao.authorization-uri}")
    private String kakaoAuthorizationUri;

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String kakaoTokenUri;

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String kakaoUserInfoUri;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret:}") // Optional
    private String kakaoClientSecret;

    private final WebClient webClient;

    @Override
    public String getKakaoAuthorizationUrl() {
        log.info(kakaoRedirectUri);
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
        // String kakaoAccessToken = getAccessToken(code);
        KakaoTokenResponse tokenResponse = getAccessTokenResponse(code);

        // 2. 액세스 토큰과 리프레시 토큰 로그 확인
        log.info("Access Token: {}", tokenResponse.accessToken());
        log.info("Refresh Token: {}", tokenResponse.refreshToken());

        // 실제 로직 추가 (사용자 정보 처리)
        // TODO: 사용자 정보를 기반으로 로그인 또는 회원가입 로직 처리
        log.info("Received access token: {}", tokenResponse);

        return KakaoLoginResponseDto.builder()
                .accountId(1L) // Mock 데이터
                .userRole("USER") // Mock 데이터
                .accessToken(tokenResponse.accessToken()) // 실제 액세스 토큰
                .refreshToken(tokenResponse.refreshToken()) // 실제 리프레시 토큰
//                .accessToken(kakaoAccessToken)
//                .refreshToken("mock-refresh-token")
                .isNewUser(false)
                .build();
    }

    private String getAccessToken(String code) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", kakaoClientId);
        formData.add("redirect_uri", kakaoRedirectUri);
        formData.add("code", code);

        // 클라이언트 시크릿이 활성화된 경우 추가
        if (kakaoClientSecret != null && !kakaoClientSecret.isBlank()) {
            formData.add("client_secret", kakaoClientSecret);
        }

        KakaoTokenResponse response = webClient.post()
                .uri(kakaoTokenUri)
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(KakaoTokenResponse.class) // TODO 실제 구현에서는 DTO로 변환 필요
                .block(); // 동기 처리

        if (response == null || response.accessToken() == null) {
            throw new IllegalStateException("Failed to retrieve access token from Kakao");
        }

        return response.accessToken();
    }

    private KakaoTokenResponse getAccessTokenResponse(String code) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", kakaoClientId);
        formData.add("redirect_uri", kakaoRedirectUri);
        formData.add("code", code);

        // Optional: client_secret 추가 (필요한 경우)
        if (kakaoClientSecret != null && !kakaoClientSecret.isBlank()) {
            formData.add("client_secret", kakaoClientSecret);
        }

        return webClient.post()
                .uri(kakaoTokenUri)
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(KakaoTokenResponse.class) // DTO로 응답 처리
                .block(); // 동기 처리
    }
}
