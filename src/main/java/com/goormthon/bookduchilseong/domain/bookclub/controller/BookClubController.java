package com.goormthon.bookduchilseong.domain.bookclub.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goormthon.bookduchilseong.domain.bookclub.dto.request.BookClubOnlyRequestDTO;
import com.goormthon.bookduchilseong.domain.bookclub.dto.request.BookClubTogetherRequestDTO;
import com.goormthon.bookduchilseong.domain.bookclub.dto.response.BookClubDetailDTO;
import com.goormthon.bookduchilseong.domain.bookclub.dto.response.BookClubResponseDTO;
import com.goormthon.bookduchilseong.domain.bookclub.service.BookClubService;
import com.goormthon.bookduchilseong.global.apiPayload.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookclubs")
public class BookClubController {

	private final BookClubService bookClubService;

	@PostMapping("/only")
	public ApiResponse<?> createBookClubOnly(@RequestBody BookClubOnlyRequestDTO bookClubOnlyRequestDTO) {
		try {
			bookClubService.createBookClubOnly(bookClubOnlyRequestDTO);
			return ApiResponse.onSuccess("북클럽 생성 성공");
		} catch (Exception e) {
			log.info(e.getMessage());
			return ApiResponse.onFailure("500", "북클럽 생성 실패", null);
		}
	}

	@PostMapping("/together")
	public ApiResponse<?> createBookClubTogether(@RequestBody BookClubTogetherRequestDTO bookClubTogetherRequestDTO) {
		try {
			bookClubService.createBookClubTogether(bookClubTogetherRequestDTO);
			return ApiResponse.onSuccess("북클럽 생성 성공");
		} catch (Exception e) {
			log.info(e.getMessage());
			return ApiResponse.onFailure("500", "북클럽 생성 실패", null);
		}
	}

	@PostMapping("/{bookclubId}/join")
	public ApiResponse<?> joinBookClub(@PathVariable Long bookclubId) {
		try {
			bookClubService.joinBookClub(bookclubId);
			return ApiResponse.onSuccess("북클럽 가입하기 성공");
		} catch (Exception e) {
			log.info(e.getMessage());
			return ApiResponse.onFailure("500", "북클럽 가입하기 실패", null);
		}
	}

	@GetMapping()
	public ApiResponse<List<BookClubResponseDTO>> getBookClubs() {
		try {
			return ApiResponse.onSuccess(bookClubService.getBookClubs());
		} catch (Exception e) {
			log.info(e.getMessage());
			return ApiResponse.onFailure("500", "북클럽 조회 실패", null);
		}
	}

	@GetMapping("/{bookclubId}")
	public ApiResponse<BookClubDetailDTO> getBookClub(@PathVariable Long bookclubId) {
		try {
			return ApiResponse.onSuccess(bookClubService.getBookClub(bookclubId));
		} catch (Exception e) {
			log.info(e.getMessage());
			return ApiResponse.onFailure("500", "북클럽 조회 실패", null);
		}
	}

	@GetMapping("{bookclubId}/progresses")
	public ApiResponse<?> getBookClubProgresses(@PathVariable Long bookclubId) {
		try {
			return ApiResponse.onSuccess(bookClubService.getBookClubProgresses(bookclubId));
		} catch (Exception e) {
			log.info(e.getMessage());
			return ApiResponse.onFailure("500", "북클럽 진행도 조회 실패", null);
		}
	}

	@GetMapping("{bookclubId}/gallery")
	public ApiResponse<?> getBookClubGallery(@PathVariable Long bookclubId) {
		try {
			return ApiResponse.onSuccess(bookClubService.getBookClubGallery(bookclubId));
		} catch (Exception e) {
			log.info(e.getMessage());
			return ApiResponse.onFailure("500", "북클럽 갤러리 조회 실패", null);
		}
	}
}
