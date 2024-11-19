package com.goormthon.bookduchilseong.domain.memo.service;

import com.goormthon.bookduchilseong.domain.certification.dto.CertificationRequestDto;
import com.goormthon.bookduchilseong.domain.certification.entity.Certification;
import com.goormthon.bookduchilseong.domain.certification.repository.CertificationRepository;
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
        // Certification 엔티티 생성 및 저장
        Memo memo = Memo.builder()
                .bookId(requestDto.getBookId())
                .image(requestDto.getImage())
                .content(requestDto.getContent())
                .build();

        // Repository를 통해 Memo 저장
        memoRepository.save(memo);

        // 성공 응답 반환
        return ApiResponse.onSuccess("메모 추가 성공");
    }
}
