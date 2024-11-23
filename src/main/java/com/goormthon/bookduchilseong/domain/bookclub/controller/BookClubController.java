package com.goormthon.bookduchilseong.domain.bookclub.controller;

import java.util.List;

import com.goormthon.bookduchilseong.global.security.jwt.JwtTokenProvider;
import org.springframework.web.bind.annotation.*;

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
public class BookClubController implements BookClubApi {

	private final BookClubService bookClubService;

	private final JwtTokenProvider jwtTokenProvider;
	@PostMapping("/only")
	public ApiResponse<?> createBookClubOnly(@RequestBody BookClubOnlyRequestDTO bookClubOnlyRequestDTO,
		@RequestHeader("Authorization") String token) {
		bookClubService.createBookClubOnly(bookClubOnlyRequestDTO, token);
		return ApiResponse.onSuccess("북클럽 생성 성공");
	}

	@PostMapping("/together")
	public ApiResponse<?> createBookClubTogether(@RequestBody BookClubTogetherRequestDTO bookClubTogetherRequestDTO,
												 @RequestHeader("Authorization") String token){
		bookClubService.createBookClubTogether(bookClubTogetherRequestDTO, token);
		return ApiResponse.onSuccess("북클럽 생성 성공");
	}

	@PostMapping("/{bookclubId}/join")
	public ApiResponse<?> joinBookClub(@PathVariable Long bookclubId,
									   @RequestHeader("Authorization") String token) {
		bookClubService.joinBookClub(bookclubId, token);
		return ApiResponse.onSuccess("북클럽 가입하기 성공");
	}

	@GetMapping()
	public ApiResponse<List<BookClubResponseDTO>> getBookClubs() {
		return ApiResponse.onSuccess(bookClubService.getBookClubs());
	}

	@GetMapping("/{bookclubId}")
	public ApiResponse<BookClubDetailDTO> getBookClub(@PathVariable Long bookclubId) {
		return ApiResponse.onSuccess(bookClubService.getBookClub(bookclubId));
	}

	@GetMapping("{bookclubId}/progresses")
	public ApiResponse<?> getBookClubProgresses(@PathVariable Long bookclubId) {
		return ApiResponse.onSuccess(bookClubService.getBookClubProgresses(bookclubId));
	}

	@GetMapping("{bookclubId}/gallery")
	public ApiResponse<?> getBookClubGallery(@PathVariable Long bookclubId) {
		return ApiResponse.onSuccess(bookClubService.getBookClubGallery(bookclubId));
	}

	@GetMapping("/joined")
	public ApiResponse<?> getJoinedBookClubs(@RequestHeader("Authorization") String token) {
		return ApiResponse.onSuccess(bookClubService.getJoinedBookClubs(token));
	}

	@GetMapping("/join")
	public ApiResponse<?> getJoinBookClubs(@RequestHeader("Authorization") String token) {
		return ApiResponse.onSuccess(bookClubService.getjoinBookClubs(token));
	}
}
