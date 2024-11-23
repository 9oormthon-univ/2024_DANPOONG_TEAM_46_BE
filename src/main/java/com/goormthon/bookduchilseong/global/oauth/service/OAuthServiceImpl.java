package com.goormthon.bookduchilseong.global.oauth.service;

import com.goormthon.bookduchilseong.domain.user.entity.User;
import com.goormthon.bookduchilseong.domain.user.service.UserService;
import com.goormthon.bookduchilseong.domain.zodiacsign.entity.Zodiacsign;
import com.goormthon.bookduchilseong.domain.zodiacsign.entity.Zodiacsigns;
import com.goormthon.bookduchilseong.domain.zodiacsign.repository.ZodiacsignRepository;
import com.goormthon.bookduchilseong.global.auth.dto.response.KakaoUserResponseDto;
import com.goormthon.bookduchilseong.global.auth.repository.AuthRepository;
import com.goormthon.bookduchilseong.global.auth.service.KakaoService;
import com.goormthon.bookduchilseong.global.oauth.dto.kakao.KakaoTokenResponse;
import com.goormthon.bookduchilseong.global.oauth.dto.response.KakaoLoginResponseDto;
import com.goormthon.bookduchilseong.global.oauth.service.OAuthService;
import com.goormthon.bookduchilseong.global.security.jwt.JwtTokenProvider;
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

    private final UserService userService;
    private final KakaoService kakaoService;
    private final WebClient webClient;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthRepository authRepository;
    private final ZodiacsignRepository zodiacsignRepository;
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
        KakaoTokenResponse tokenResponse = getAccessTokenResponse(code);
        // 액세스 토큰과 리프레시 토큰 로그 확인
        log.info("Kakao Access Token : {}", tokenResponse.accessToken());
        log.info("Kakao Refresh Token : {}", tokenResponse.refreshToken());

        // 2. 카카오 사용자 정보 가져오기
        KakaoUserResponseDto kakaoUser = kakaoService.getKakaoUser(tokenResponse.accessToken());
        log.info("Profile Nickname : {}", kakaoUser.properties().nickname());
        log.info("Profile Thumbnail uri : {}", kakaoUser.properties().thumbnailImage());

        // 3. 사용자 조회 또는 생성
        User user = userService.findOrCreateUser(kakaoUser);

//        // 3. Zodiacsign 정보 저장
//        for (Zodiacsigns zodiac : Zodiacsigns.values()) {
//            Zodiacsign zodiacsign = Zodiacsign.builder()
//                    .zodiacsigns(zodiac)
//                    .status(false) // 초기 상태는 비활성화
//                    .zodiacsignImg(zodiac.getImagePath()) // 이미지 경로 설정
//                    .user(user)
//                    .build();
//            zodiacsignRepository.save(zodiacsign);
//        }
        // 4. JWT 발급
        String jwtAccessToken = jwtTokenProvider.createAccessToken(user.getId());
        String jwtRefreshToken = jwtTokenProvider.createRefreshToken(user.getId());
        log.info("Jwt Access Token : {}", jwtAccessToken);
        log.info("Jwt Refresh Token : {}", jwtRefreshToken);

        // 5. Refresh Token 저장
        authRepository.saveRefreshToken(
                user.getId(),
                jwtRefreshToken,
                jwtTokenProvider.getRefreshTokenValidity()
        );

        return KakaoLoginResponseDto.builder()
                .accessToken(jwtAccessToken) // 반환해주는 jwtAccessToken
                .refreshToken(jwtRefreshToken) // 반환해주는 jwtRefreshToken
                .build();
    }

    private KakaoTokenResponse getAccessTokenResponse(String code) {
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
                .bodyToMono(KakaoTokenResponse.class) // DTO로 응답 처리
                .block(); // 동기 처리

        if (response == null) {
            throw new IllegalStateException("Failed to retrieve access token from Kakao");
        }

        return response;
    }
}
