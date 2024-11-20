package com.goormthon.bookduchilseong.domain.certification.controller;

import com.goormthon.bookduchilseong.domain.certification.dto.CertificationRequestDto;
import com.goormthon.bookduchilseong.global.apiPayload.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Certification API", description = "도서 인증 관련 API")
public interface CertificationApi {

	@Operation(
		summary = "도서 인증 생성",
		description = "특정 도서에 대한 인증을 생성합니다."
	)
	@PostMapping
	ApiResponse<?> createCertification(
		@Parameter(description = "도서 ID", required = true, example = "1")
		@PathVariable Long bookId,
		@Parameter(description = "도서 인증 요청 데이터", required = true)
		@RequestBody CertificationRequestDto requestDto
	);
}