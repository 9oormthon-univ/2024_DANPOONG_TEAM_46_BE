package com.goormthon.bookduchilseong.domain.certification.service;

import com.goormthon.bookduchilseong.domain.certification.dto.CertificationRequestDto;
import com.goormthon.bookduchilseong.domain.certification.entity.Certification;
import com.goormthon.bookduchilseong.domain.certification.repository.CertificationRepository;
import com.goormthon.bookduchilseong.domain.certification.service.CertificationService;
import com.goormthon.bookduchilseong.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CertificationServiceImpl implements CertificationService {

    private final CertificationRepository certificationRepository;

    @Override
    public ApiResponse<String> createCertification(Long bookId, CertificationRequestDto requestDto) {
        // Certification 엔티티 생성 및 저장
        Certification certification = Certification.builder()
                .bookId(bookId)
                .startPage(requestDto.getStartPage())
                .endPage(requestDto.getEndPage())
                .image(requestDto.getImage())
                .paragraph(requestDto.getParagraph())
                .build();

        certificationRepository.save(certification);

        // 성공 응답 반환
        return ApiResponse.onSuccess("도서 인증하기 성공");
    }
}
