package com.goormthon.bookduchilseong.domain.bookclub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goormthon.bookduchilseong.domain.bookclub.dto.request.BookClubResqeustDTO;
import com.goormthon.bookduchilseong.domain.bookclub.service.BookClubService;
import com.goormthon.bookduchilseong.global.apiPayload.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookclub")
public class BookClubController {

	private final BookClubService bookClubService;

	@PostMapping("/bookclubs")
	public ApiResponse<?> createBookClub(@RequestBody BookClubResqeustDTO bookClubResqeustDTO) {
		try {
			bookClubService.createBookClub(bookClubResqeustDTO);
			return ApiResponse.onSuccess("북클럽 생성 성공");
		} catch (Exception e) {
			log.info(e.getMessage());
			return ApiResponse.onFailure("500", "북클럽 생성 실패", null);
		}
	}

	@PostMapping("/bookclubs/{bookclubId}")
	public ApiResponse<?> joinBookClub(
		@PathVariable Long bookclubId) {
		try {
			bookClubService.joinBookClub(bookclubId);
			return ApiResponse.onSuccess("북클럽 가입하기 성공");
		} catch (Exception e) {
			log.info(e.getMessage());
			return ApiResponse.onFailure("500", "북클럽 가입하기 실패", null);
		}
	}
}
