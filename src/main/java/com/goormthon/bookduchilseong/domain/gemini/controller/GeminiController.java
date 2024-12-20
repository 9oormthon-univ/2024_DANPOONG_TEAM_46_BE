package com.goormthon.bookduchilseong.domain.gemini.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.goormthon.bookduchilseong.domain.gemini.service.GeminiService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gemini")
public class GeminiController {

	private final GeminiService geminiService;

	@GetMapping("/chat")
	public ResponseEntity<?> gemini(@RequestParam(name = "message") String message) {
		try {
			return ResponseEntity.ok().body(geminiService.getContents(message));
		} catch (HttpClientErrorException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}