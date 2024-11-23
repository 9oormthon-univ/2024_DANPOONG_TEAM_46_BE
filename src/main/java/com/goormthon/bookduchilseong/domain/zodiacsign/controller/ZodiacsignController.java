package com.goormthon.bookduchilseong.domain.zodiacsign.controller;

import org.springframework.web.bind.annotation.*;

import com.goormthon.bookduchilseong.domain.zodiacsign.service.ZodiacsignService;
import com.goormthon.bookduchilseong.global.apiPayload.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/zodiac-signs")
public class ZodiacsignController implements ZodiacsignApi {

	private final ZodiacsignService zodiacsignService;

	@GetMapping("/my-zodiacsigns")
	public ApiResponse<?> getMyZodiacsigns(@RequestHeader("Authrization") String token) {
		return ApiResponse.onSuccess(zodiacsignService.getMyZodiacsigns(token));
	}

	@GetMapping("/{zodiacsignId}")
	public ApiResponse<?> getDetailZodiacsign(@RequestParam("zodiacsignId") Long zodiacsignId) {
		return ApiResponse.onSuccess(zodiacsignService.getDetailZodiacsign(zodiacsignId));
	}

	@PatchMapping("/{zodiacsignId}/profile")
	public ApiResponse<?> updateProfile(@PathVariable("zodiacsignId") Long zodiacsignId,
										@RequestHeader("Authrization") String token) {
		zodiacsignService.updateProfile(zodiacsignId, token);
		return ApiResponse.onSuccess("프로필 지정 성공");
	}

	@PostMapping("/draw")
	public ApiResponse<?> drawZodiacsign(@RequestHeader("Authrization") String token) {
		zodiacsignService.drawZodiacsign(token);
		return ApiResponse.onSuccess("별자리 뽑기 성공");
	}
}
