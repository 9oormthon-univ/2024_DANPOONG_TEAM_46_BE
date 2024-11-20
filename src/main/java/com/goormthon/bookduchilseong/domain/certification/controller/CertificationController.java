package com.goormthon.bookduchilseong.domain.certification.controller;

import com.goormthon.bookduchilseong.domain.certification.dto.CertificationRequestDto;
import com.goormthon.bookduchilseong.domain.certification.service.CertificationService;
import com.goormthon.bookduchilseong.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books/{bookId}/certification")
@RequiredArgsConstructor
public class CertificationController implements CertificationApi {

	private final CertificationService certificationService;

	@PostMapping
	public ApiResponse<?> createCertification(
		@PathVariable Long bookId,
		@RequestBody CertificationRequestDto requestDto) {

		try {
			return certificationService.createCertification(bookId, requestDto);
		} catch (RuntimeException e) {
			return ApiResponse.onFailure("500", "도서 인증 실패: " + e.getMessage(), null);
		}
	}
}