package com.goormthon.bookduchilseong.domain.certification.controller;

import com.goormthon.bookduchilseong.global.security.jwt.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping
	public ApiResponse<?> createCertification(@RequestHeader(name = "Authorization") String token, @PathVariable Long bookId,
											  @RequestBody CertificationRequestDTO requestDto) {
		Long userId = jwtTokenProvider.getUserIdFromToken(token);
		return certificationService.createCertification(userId, bookId, requestDto);
	}

	@PostMapping("/summation")
	public ResponseEntity<String> createSummation(@RequestParam(name = "certficationId") Long certficationId,
		@RequestParam("image") MultipartFile image) {
		String extractedText = certificationService.createSummation(certficationId, image);
		return ResponseEntity.ok(extractedText);
	}
}