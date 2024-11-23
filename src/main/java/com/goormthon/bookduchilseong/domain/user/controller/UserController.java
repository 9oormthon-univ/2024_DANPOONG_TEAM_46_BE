package com.goormthon.bookduchilseong.domain.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.goormthon.bookduchilseong.domain.user.dto.response.UserDrawResponseDTO;
import com.goormthon.bookduchilseong.domain.user.dto.response.UserMyPageReseponseDto;
import com.goormthon.bookduchilseong.domain.user.service.UserService;
import com.goormthon.bookduchilseong.global.apiPayload.ApiResponse;
import com.goormthon.bookduchilseong.global.security.jwt.JwtTokenProvider;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {
	private final UserService userService; // UserService 인터페이스 의존
	private final JwtTokenProvider jwtTokenProvider;

	@Operation(
		summary = "마이페이지 화면",
		description = "(별자리, 읽은 책, 가입북클럽) 수, 내 책 목록 반환"
	)
	@GetMapping("/mypage")
	public ApiResponse<?> getUserMyPage(@RequestHeader(name = "Authorization") String token) {
		try {
			Long userId = jwtTokenProvider.getUserIdFromToken(token);
			UserMyPageReseponseDto response = userService.getUserMypage(userId);
			return ApiResponse.onSuccess(response);
		} catch (Exception e) {
			log.error("Error retrieving mypage: {}", e.getMessage());
			return ApiResponse.onFailure("500", "내 서재/마이페이지 조회 실패", null);
		}
	}

	@Operation(
		summary = "뽑기 화면",
		description = "뽑기 횟수 반환"
	)
	@GetMapping("/draw")
	public ApiResponse<?> getDraw(@RequestHeader(name = "Authorization") String token) {
		Long userId = jwtTokenProvider.getUserIdFromToken(token);
		log.info("userId: {}", userId);
		UserDrawResponseDTO response = userService.getDraw(userId);
		return ApiResponse.onSuccess(response);
	}

}
