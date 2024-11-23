package com.goormthon.bookduchilseong.domain.ocr.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.goormthon.bookduchilseong.domain.ocr.service.OCRService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ocr")
public class OCRController {

	private final OCRService ocrService;

	// 단일 이미지 텍스트 추출
	@PostMapping("/extract-text")
	public ResponseEntity<String> extractText(@RequestParam("images") MultipartFile image) {
		if (image.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No image file provided");
		}

		try {
			String extractedText = ocrService.execute(image);
			return ResponseEntity.ok(extractedText);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error extracting text: " + e.getMessage());
		}
	}
}