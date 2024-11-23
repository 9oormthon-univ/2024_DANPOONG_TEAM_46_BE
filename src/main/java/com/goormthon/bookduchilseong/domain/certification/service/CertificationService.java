package com.goormthon.bookduchilseong.domain.certification.service;

import org.springframework.web.multipart.MultipartFile;

import com.goormthon.bookduchilseong.domain.certification.dto.request.CertificationRequestDTO;
import com.goormthon.bookduchilseong.global.apiPayload.ApiResponse;

public interface CertificationService {
	ApiResponse<String> createCertification(Long userId, Long bookId, CertificationRequestDTO requestDto);

	String createSummation(Long certficationId, MultipartFile image);
}