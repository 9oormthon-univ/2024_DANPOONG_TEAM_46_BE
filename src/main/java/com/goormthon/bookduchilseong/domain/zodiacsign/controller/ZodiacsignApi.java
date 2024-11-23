package com.goormthon.bookduchilseong.domain.zodiacsign.controller;

import org.springframework.web.bind.annotation.*;

import com.goormthon.bookduchilseong.global.apiPayload.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Zodiac Sign API", description = "별자리 관련 API")
public interface ZodiacsignApi {

	@Operation(summary = "내 별자리 목록 조회", description = "사용자가 소유한 별자리 목록을 조회합니다.")
	@GetMapping("/my-zodiacsigns")
	ApiResponse<?> getMyZodiacsigns(
			@Parameter(description = "JWT 토큰", required = true)
			@RequestHeader("Authrization") String token
	);

	@Operation(summary = "별자리 상세 조회", description = "특정 별자리의 상세 정보를 조회합니다.")
	@GetMapping("/{zodiacsignId}")
	ApiResponse<?> getDetailZodiacsign(
		@Parameter(description = "별자리 ID", required = true)
		@PathVariable("zodiacsignId") Long zodiacsignId
	);

	@Operation(summary = "별자리 프로필 지정", description = "특정 별자리를 프로필로 설정합니다.")
	@PostMapping("/{zodiacsignId}/profile")
	ApiResponse<?> updateProfile(
		@Parameter(description = "별자리 ID", required = true)
		@PathVariable("zodiacsignId") Long zodiacsignId,
		@Parameter(description = "JWT 토큰", required = true)
		@RequestHeader("Authrization") String token
	);

	@Operation(summary = "별자리 뽑기", description = "사용자에게 랜덤으로 별자리를 추첨합니다.")
	@PostMapping("/draw")
	ApiResponse<?> drawZodiacsign(
			@Parameter(description = "JWT 토큰", required = true)
			@RequestHeader("Authrization") String token
	);
}