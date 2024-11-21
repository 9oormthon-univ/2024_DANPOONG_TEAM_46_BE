package com.goormthon.bookduchilseong.domain.memo.service;

import com.goormthon.bookduchilseong.domain.memo.dto.request.MemoRequestDTO;
import com.goormthon.bookduchilseong.global.apiPayload.ApiResponse;

public interface MemoService {
	ApiResponse<?> createMemo(MemoRequestDTO requestDto);
}