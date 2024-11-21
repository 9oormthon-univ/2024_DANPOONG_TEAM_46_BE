package com.goormthon.bookduchilseong.domain.certification.service;

import com.goormthon.bookduchilseong.domain.certification.dto.request.CertificationRequestDTO;
import com.goormthon.bookduchilseong.global.apiPayload.ApiResponse;

public interface CertificationService {
	ApiResponse<String> createCertification(Long bookId, CertificationRequestDTO requestDto);
}