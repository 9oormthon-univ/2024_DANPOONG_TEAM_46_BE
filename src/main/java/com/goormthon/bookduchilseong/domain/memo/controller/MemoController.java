package com.goormthon.bookduchilseong.domain.memo.controller;

import com.goormthon.bookduchilseong.domain.memo.dto.request.MemoRequestDTO;
import com.goormthon.bookduchilseong.domain.memo.service.MemoService;
import com.goormthon.bookduchilseong.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/memos")
@RequiredArgsConstructor
public class MemoController implements MemoApi{

	private final MemoService memoService;

	//    @PostMapping
	//    public ApiResponse<?> createMemo(@RequestBody MemoRequestDto requestDto) {
	//        return memoService.createMemo(requestDto);
	//    }

	@PostMapping
	public ApiResponse<?> createMemo(@RequestBody MemoRequestDTO requestDto) {
		return memoService.createMemo(requestDto);
	}
}