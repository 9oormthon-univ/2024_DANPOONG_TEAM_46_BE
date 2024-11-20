package com.goormthon.bookduchilseong.domain.bookclub.controller;

import com.goormthon.bookduchilseong.domain.bookclub.dto.request.BookClubOnlyRequestDTO;
import com.goormthon.bookduchilseong.domain.bookclub.dto.request.BookClubTogetherRequestDTO;
import com.goormthon.bookduchilseong.domain.bookclub.dto.response.BookClubDetailDTO;
import com.goormthon.bookduchilseong.domain.bookclub.dto.response.BookClubResponseDTO;
import com.goormthon.bookduchilseong.global.apiPayload.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Book Club API", description = "북클럽 관련 API")
public interface BookClubApi {

	@Operation(summary = "혼자 북클럽 생성", description = "혼자 읽는 북클럽을 생성합니다.")
	@PostMapping("/only")
	ApiResponse<?> createBookClubOnly(
		@Parameter(description = "혼자 읽는 북클럽 요청 데이터", required = true)
		@RequestBody BookClubOnlyRequestDTO bookClubOnlyRequestDTO,
		@Parameter(description = "사용자 ID", required = true, example = "1")
		@RequestParam("userId") Long userId
	);

	@Operation(summary = "함께하는 북클럽 생성", description = "함께 읽는 북클럽을 생성합니다.")
	@PostMapping("/together")
	ApiResponse<?> createBookClubTogether(
		@Parameter(description = "함께 읽는 북클럽 요청 데이터", required = true)
		@RequestBody BookClubTogetherRequestDTO bookClubTogetherRequestDTO,
		@Parameter(description = "사용자 ID", required = true, example = "1")
		@RequestParam("userId") Long userId
	);

	@Operation(summary = "북클럽 가입", description = "북클럽에 사용자가 가입합니다.")
	@PostMapping("/{bookclubId}/join")
	ApiResponse<?> joinBookClub(
		@Parameter(description = "북클럽 ID", required = true, example = "1")
		@PathVariable Long bookclubId,
		@Parameter(description = "사용자 ID", required = true, example = "1")
		@RequestParam("userId") Long userId
	);

	@Operation(summary = "북클럽 목록 조회", description = "전체 북클럽 목록을 조회합니다.")
	@GetMapping
	ApiResponse<List<BookClubResponseDTO>> getBookClubs();

	@Operation(summary = "북클럽 상세 조회", description = "특정 북클럽의 상세 정보를 조회합니다.")
	@GetMapping("/{bookclubId}")
	ApiResponse<BookClubDetailDTO> getBookClub(
		@Parameter(description = "북클럽 ID", required = true, example = "1")
		@PathVariable Long bookclubId
	);

	@Operation(summary = "북클럽 진행도 조회", description = "특정 북클럽의 진행도를 조회합니다.")
	@GetMapping("/{bookclubId}/progresses")
	ApiResponse<?> getBookClubProgresses(
		@Parameter(description = "북클럽 ID", required = true, example = "1")
		@PathVariable Long bookclubId
	);

	@Operation(summary = "북클럽 갤러리 조회", description = "특정 북클럽의 갤러리 이미지를 조회합니다.")
	@GetMapping("/{bookclubId}/gallery")
	ApiResponse<?> getBookClubGallery(
		@Parameter(description = "북클럽 ID", required = true, example = "1")
		@PathVariable Long bookclubId
	);

	@Operation(summary = "사용자가 이전에 가입한 북클럽 목록 조회", description = "사용자가 이전에 가입했던 북클럽 목록을 조회합니다.")
	@GetMapping("/joined")
	ApiResponse<?> getJoinedBookClubs(
		@Parameter(description = "사용자 ID", required = true, example = "1")
		@RequestParam("userId") Long userId
	);

	@Operation(summary = "사용자가 현재 가입한 북클럽 조회", description = "사용자가 현재 참여 중인 북클럽 목록을 조회합니다.")
	@GetMapping("/join")
	ApiResponse<?> getJoinBookClubs(
		@Parameter(description = "사용자 ID", required = true, example = "1")
		@RequestParam("userId") Long userId
	);
}