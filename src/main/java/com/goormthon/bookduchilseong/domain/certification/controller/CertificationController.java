package com.goormthon.bookduchilseong.domain.certification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.goormthon.bookduchilseong.domain.certification.dto.request.CertificationRequestDTO;
import com.goormthon.bookduchilseong.domain.certification.service.CertificationService;
import com.goormthon.bookduchilseong.global.apiPayload.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/books/{bookId}/certification")
@RequiredArgsConstructor
public class CertificationController implements CertificationApi {

	private final CertificationService certificationService;

	@PostMapping
	public ApiResponse<?> createCertification(@RequestParam(name = "userId") Long userId, @PathVariable Long bookId,
		@RequestBody CertificationRequestDTO requestDto) {
		return certificationService.createCertification(userId, bookId, requestDto);
	}

	@PostMapping("/summation")
	public ResponseEntity<String> createSummation(@RequestParam(name = "certficationId") Long certficationId,
		@RequestParam("image") MultipartFile image) {
		String extractedText = certificationService.createSummation(certficationId, image);
		return ResponseEntity.ok(extractedText);
	}
}