package com.goormthon.bookduchilseong.domain.memo.service;

import com.goormthon.bookduchilseong.domain.memo.dto.MemoRequestDto;
import com.goormthon.bookduchilseong.global.apiPayload.ApiResponse;

public interface MemoService {
    ApiResponse<?> createMemo(MemoRequestDto requestDto);
}
