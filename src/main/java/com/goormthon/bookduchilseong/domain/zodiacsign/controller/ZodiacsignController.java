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
		try {
			return ApiResponse.onSuccess(zodiacsignService.getMyZodiacsigns(userId));
		} catch (Exception e) {
			log.info(e.getMessage());
			return ApiResponse.onFailure("500", "내 별자리 조회 실패", null);
		}
	}

	@GetMapping("/{zodiacsignId}")
	public ApiResponse<?> getDetailZodiacsign(@RequestParam("zodiacsignId") Long zodiacsignId) {
		try {
			return ApiResponse.onSuccess(zodiacsignService.getDetailZodiacsign(zodiacsignId));
		} catch (Exception e) {
			log.info(e.getMessage());
			return ApiResponse.onFailure("500", "별자리 조회 실패", null);
		}
	}
	@PatchMapping("/{zodiacsignId}/profile")
	public ApiResponse<?> updateProfile(@PathVariable("zodiacsignId") Long zodiacsignId,
		@RequestParam("userId") Long userId) {
		try {
			zodiacsignService.updateProfile(zodiacsignId, userId);
			return ApiResponse.onSuccess("프로필 지정 성공");
		} catch (Exception e) {
			log.info(e.getMessage());
			return ApiResponse.onFailure("500", "내 별자리 조회 실패", null);
		}
	}
}
