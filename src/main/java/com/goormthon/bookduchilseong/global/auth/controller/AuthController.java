package com.goormthon.bookduchilseong.global.auth.controller;

import com.goormthon.bookduchilseong.global.apiPayload.ApiResponse;
import com.goormthon.bookduchilseong.global.auth.dto.request.TokenRefreshRequestDto;
import com.goormthon.bookduchilseong.global.auth.dto.response.AccountLoginResponseDto;
import com.goormthon.bookduchilseong.global.auth.dto.response.TokenRefreshResponseDto;
import com.goormthon.bookduchilseong.global.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/refresh")
    public ApiResponse<?> refreshAccessToken(
            @RequestBody TokenRefreshRequestDto requestDto){
        try{
            // AuthService를 통해 새 토큰 생성
            TokenRefreshResponseDto response = authService.refreshToken(requestDto.getRefreshToken());
            return ApiResponse.onSuccess(response);
        }catch (RuntimeException e){
            return ApiResponse.onFailure("400", "토큰 refresh 실패",null);
        }
    }

    // 일반 유저 로그인
    @PostMapping("/login")
    public ResponseEntity<AccountLoginResponseDto> login(
            @RequestHeader("Authorization") String accessToken,
            @RequestParam("draw") String draw){
        String token = accessToken.replace("Bearer ", "");
        AccountLoginResponseDto response = authService.login(token);
        return ResponseEntity.ok(response);
    }
}
