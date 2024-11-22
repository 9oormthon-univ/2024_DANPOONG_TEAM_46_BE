package com.goormthon.bookduchilseong.global.auth.service;

import com.goormthon.bookduchilseong.global.auth.dto.response.KakaoUserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.aspectj.bridge.MessageUtil.print;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoServiceImpl implements KakaoService {

    private final RestTemplate restTemplate;

    public KakaoUserResponseDto getKakaoUser(String accessToken)
    {
        if (accessToken == null || accessToken.isBlank()) {
            throw new IllegalArgumentException("카카오 토큰을 넘겨받지 못함");
        }

        String userInfoUri = "https://kapi.kakao.com/v2/user/me";

        // Spring의 HttpHeaders 사용
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken); // Bearer 토큰 설정

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> rawResponse = restTemplate.exchange(
                userInfoUri, HttpMethod.GET, entity, String.class
        );
        // 원본 JSON 로그 출력
        log.info("Raw Kakao Response JSON: {}", rawResponse.getBody());

        if (!rawResponse.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to fetch Kakao user info.");
        }

        // JSON 데이터를 KakaoUserResponseDto로 변환
        ResponseEntity<KakaoUserResponseDto> response = restTemplate.exchange(
                userInfoUri, HttpMethod.GET, entity, KakaoUserResponseDto.class
        );

        KakaoUserResponseDto kakaoUser = response.getBody();

        return response.getBody();
    }
}
