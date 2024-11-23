package com.goormthon.bookduchilseong.global.oauth.controller;

import com.goormthon.bookduchilseong.global.oauth.dto.response.KakaoLoginResponseDto;
import com.goormthon.bookduchilseong.global.oauth.dto.response.KakaoLoginUrlResponseDto;
import com.goormthon.bookduchilseong.global.oauth.service.OAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/oauth")
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthService oAuthService;

    @Operation(
            summary = "[공통] 카카오 로그인 URL 반환",
            description = "클라이언트가 카카오 로그인 화면으로 이동할 수 있는 URL을 반환합니다. 해당 URL로 이동하여 카카오 인증을 진행하십시오.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "카카오 로그인 URL 반환 성공")
            }
    )
    @GetMapping("/kakao")
    public ResponseEntity<KakaoLoginUrlResponseDto> kakaoLogin() {
        String authorizationUrl = oAuthService.getKakaoAuthorizationUrl();
        return ResponseEntity.ok(
                KakaoLoginUrlResponseDto.builder()
                        .authorizationUrl(authorizationUrl)
                        .build()
        );
    }

    @Operation(
            summary = "[공통] 카카오 콜백 처리",
            description = "카카오 인증 서버가 리다이렉트한 사용자 요청을 처리합니다. 요청에 포함된 인증 코드를 이용해 로그인 또는 회원가입을 수행하고, 결과를 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인 또는 회원가입 성공 시 사용자 정보 반환"),
            }
    )
    @GetMapping("/kakao/callback")
    public ResponseEntity<KakaoLoginResponseDto> kakaoCallback(@RequestParam("code") String code) {
        KakaoLoginResponseDto response = oAuthService.processKakaoCallback(code);
        return ResponseEntity.ok(response);
    }
}
