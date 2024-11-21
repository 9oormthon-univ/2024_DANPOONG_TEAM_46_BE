package com.goormthon.bookduchilseong.domain.memo.controller;

import com.goormthon.bookduchilseong.domain.memo.dto.request.MemoRequestDTO;
import com.goormthon.bookduchilseong.global.apiPayload.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Memo API", description = "메모 관련 API")
public interface MemoApi {

	@Operation(
		summary = "메모 생성",
		description = "사용자가 메모를 생성합니다."
	)
	@PostMapping
	ApiResponse<?> createMemo(
		@Parameter(description = "메모 생성 요청 데이터", required = true)
		@RequestBody MemoRequestDTO requestDto
	);
}