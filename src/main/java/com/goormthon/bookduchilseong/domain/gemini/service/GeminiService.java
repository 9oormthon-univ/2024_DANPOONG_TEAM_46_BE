package com.goormthon.bookduchilseong.domain.gemini.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.goormthon.bookduchilseong.domain.gemini.dto.request.ChatRequestDTO;
import com.goormthon.bookduchilseong.domain.gemini.dto.response.ChatResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GeminiService {

	@Qualifier("GeminiRestTemplate")
	@Autowired
	private RestTemplate restTemplate;

	@Value("${spring.gemini.api.url}")
	private String apiUrl;

	@Value("${spring.gemini.api.key}")
	private String geminiApiKey;

	public String getContents(String prompt) {

		String requestUrl = apiUrl + "?key=" + geminiApiKey;

		ChatRequestDTO request = new ChatRequestDTO(prompt);
		ChatResponseDTO response = restTemplate.postForObject(requestUrl, request, ChatResponseDTO.class);

		String message = response.getCandidates().get(0).getContent().getParts().get(0).getText().toString();

		return message;
	}
}