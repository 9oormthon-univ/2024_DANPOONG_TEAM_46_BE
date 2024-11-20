package com.goormthon.bookduchilseong.domain.book.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.goormthon.bookduchilseong.domain.book.dto.request.BookRequestDto;
import com.goormthon.bookduchilseong.global.apiPayload.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Book API", description = "도서 관련 API")
public interface BookApi {

	@Operation(summary = "도서 추가", description = "새로운 도서를 추가합니다.")
	@PostMapping
	ApiResponse<?> addBook(
		@Parameter(description = "추가할 도서 정보", required = true)
		@RequestBody BookRequestDto requestDto
	);

	@Operation(summary = "전체 도서 조회", description = "특정 사용자의 모든 도서를 조회합니다.")
	@GetMapping
	ApiResponse<?> getAllBooks(
		@Parameter(description = "사용자 ID", required = true, example = "1")
		@RequestParam Long userId
	);

	@Operation(summary = "도서 상세 조회", description = "특정 도서의 상세 정보를 조회합니다.")
	@GetMapping("/{bookId}")
	ApiResponse<?> getBookDetail(
		@Parameter(description = "도서 ID", required = true, example = "1")
		@PathVariable Long bookId
	);

	@Operation(summary = "도서 삭제", description = "특정 도서를 삭제합니다.")
	@DeleteMapping("/{bookId}")
	ApiResponse<?> deleteBook(
		@Parameter(description = "삭제할 도서 ID", required = true, example = "1")
		@PathVariable Long bookId
	);

	@Operation(summary = "도서 상태 업데이트", description = "특정 도서의 읽기 상태를 업데이트합니다.")
	@PatchMapping("/{bookId}")
	ApiResponse<?> updateBookStatus(
		@Parameter(description = "도서 ID", required = true, example = "1")
		@PathVariable Long bookId,
		@Parameter(description = "변경할 읽기 상태 (예: 'NOT_STARTED', 'IN_PROGRESS', 'COMPLETED')", required = true)
		@RequestParam("readStatus") String readStatus
	);
}