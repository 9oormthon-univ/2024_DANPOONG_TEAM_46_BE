package com.goormthon.bookduchilseong.domain.zodiacsign.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public ApiResponse<?> getMyZodiacsigns(@RequestParam("userId") Long userId) {
		return ApiResponse.onSuccess(zodiacsignService.getMyZodiacsigns(userId));
	}

	@GetMapping("/{zodiacsignId}")
	public ApiResponse<?> getDetailZodiacsign(@RequestParam("zodiacsignId") Long zodiacsignId) {
		return ApiResponse.onSuccess(zodiacsignService.getDetailZodiacsign(zodiacsignId));
	}

	@PatchMapping("/{zodiacsignId}/profile")
	public ApiResponse<?> updateProfile(@PathVariable("zodiacsignId") Long zodiacsignId,
		@RequestParam("userId") Long userId) {
		zodiacsignService.updateProfile(zodiacsignId, userId);
		return ApiResponse.onSuccess("프로필 지정 성공");
	}

	@PostMapping("/draw")
	public ApiResponse<?> drawZodiacsign(@RequestParam("userId") Long userId) {
		zodiacsignService.drawZodiacsign(userId);
		return ApiResponse.onSuccess("별자리 뽑기 성공");
	}
}
