package com.goormthon.bookduchilseong.domain.memo.controller;

import com.goormthon.bookduchilseong.domain.certification.dto.CertificationRequestDto;
import com.goormthon.bookduchilseong.domain.memo.dto.MemoRequestDto;
import com.goormthon.bookduchilseong.domain.memo.service.MemoService;
import com.goormthon.bookduchilseong.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/memos")
@RequiredArgsConstructor
public class MemoController {

	private final MemoService memoService;

	//    @PostMapping
	//    public ApiResponse<?> createMemo(@RequestBody MemoRequestDto requestDto) {
	//        return memoService.createMemo(requestDto);
	//    }

	@PostMapping
	public ApiResponse<?> createMemo(@RequestBody MemoRequestDto requestDto) {
		try {
			return memoService.createMemo(requestDto);
		}
		catch (RuntimeException e){
			return ApiResponse.onFailure("500", "메모 생성 실패: " + e.getMessage(), null);
		}
	}
}