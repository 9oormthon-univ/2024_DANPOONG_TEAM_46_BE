package com.goormthon.bookduchilseong.domain.certification.service;

import com.goormthon.bookduchilseong.domain.certification.dto.CertificationRequestDto;
import com.goormthon.bookduchilseong.global.apiPayload.ApiResponse;

public interface CertificationService {
    ApiResponse<String> createCertification(Long bookId, CertificationRequestDto requestDto);
}