package com.goormthon.bookduchilseong.domain.book.controller;

import com.goormthon.bookduchilseong.domain.book.dto.request.BookRequestDto;
import com.goormthon.bookduchilseong.domain.book.dto.request.BookResponseDto;
import com.goormthon.bookduchilseong.domain.book.entity.ReadStatus;
import com.goormthon.bookduchilseong.domain.book.service.BookService;
import com.goormthon.bookduchilseong.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;


import java.util.List;
@Slf4j // Lombok의 Slf4j 어노테이션 추가
@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController implements BookApi {

	private final BookService bookService;

	@PostMapping
	public ApiResponse<?> addBook(@RequestBody BookRequestDto requestDto) {
		try {
			BookResponseDto responseDto = bookService.addBook(requestDto);
			return ApiResponse.onSuccess(responseDto);
		} catch (RuntimeException e) {
			return ApiResponse.onFailure("404", e.getMessage(), null);
		}
	}

	@GetMapping
	public ApiResponse<?> getAllBooks(@RequestParam Long userId) {
		try {
			List<BookResponseDto> books = bookService.getAllBooks(userId);
			return ApiResponse.onSuccess(books);
		} catch (RuntimeException e) {
			return ApiResponse.onFailure("404", e.getMessage(), null);
		}
	}

	@GetMapping("/{bookId}")
	public ApiResponse<?> getBookDetail(@PathVariable Long bookId) {
		try {
			BookResponseDto bookDetail = bookService.getBookDetail(bookId);
			return ApiResponse.onSuccess(bookDetail);
		} catch (RuntimeException e) {
			return ApiResponse.onFailure("404", e.getMessage(), null);
		}
	}

	@DeleteMapping("/{bookId}")
	public ApiResponse<?> deleteBook(@PathVariable Long bookId) {
		try {
			bookService.deleteBook(bookId);
			return ApiResponse.onSuccess(null);
		} catch (RuntimeException e) { // 책이 없을 때 발생하는 예외 처리
			return ApiResponse.onFailure("404", e.getMessage(), null);
		}
	}

	@PatchMapping("/{bookId}")
	public ApiResponse<?> updateBookStatus(
		@PathVariable Long bookId,
		@RequestParam("readStatus") String readStatus) {
		try {
			bookService.updateBookStatus(bookId, readStatus); // 상태 업데이트 처리
			return ApiResponse.onSuccess("도서 상태가 '" + readStatus + "'로 변경되었습니다.");
		} catch (IllegalArgumentException e) { // 유효하지 않은 상태값 처리
			log.info(e.getMessage()); // 예외 로그 출력
			return ApiResponse.onFailure("400", "유효하지 않은 도서 상태입니다: " + readStatus, null);
		} catch (RuntimeException e) { // 도서를 찾지 못한 경우 처리
			log.info(e.getMessage()); // 예외 로그 출력
			return ApiResponse.onFailure("404", e.getMessage(), null);
		}
	}
}