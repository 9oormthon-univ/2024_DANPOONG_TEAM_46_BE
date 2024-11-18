package com.goormthon.bookduchilseong.domain.memo.service;

import com.goormthon.bookduchilseong.domain.memo.dto.MemoRequestDto;
import com.goormthon.bookduchilseong.domain.memo.entity.Memo;
import com.goormthon.bookduchilseong.domain.memo.repository.MemoRepository;
import com.goormthon.bookduchilseong.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemoServiceImpl implements MemoService {

    private final MemoRepository memoRepository;

    @Override
    public ApiResponse<?> createMemo(MemoRequestDto requestDto) {
        Memo memo = Memo.builder()
                .bookId(requestDto.getBookId())
                .image(requestDto.getImage())
                .content(requestDto.getContent())
                .build();

        memoRepository.save(memo);

        return ApiResponse.onSuccess("메모 추가 성공");
    }
}
