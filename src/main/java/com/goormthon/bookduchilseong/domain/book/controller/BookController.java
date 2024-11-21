package com.goormthon.bookduchilseong.domain.book.controller;

import com.goormthon.bookduchilseong.domain.book.dto.request.BookRequestDTO;
import com.goormthon.bookduchilseong.domain.book.dto.response.BookResponseDTO;
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
	public ApiResponse<?> addBook(@RequestBody BookRequestDTO requestDto) {
		BookResponseDTO responseDto = bookService.addBook(requestDto);
		return ApiResponse.onSuccess(responseDto);
	}

	@GetMapping
	public ApiResponse<?> getAllBooks(@RequestParam Long userId) {
		List<BookResponseDTO> books = bookService.getAllBooks(userId);
		return ApiResponse.onSuccess(books);
	}

	@GetMapping("/{bookId}")
	public ApiResponse<?> getBookDetail(@PathVariable Long bookId) {
		BookResponseDTO bookDetail = bookService.getBookDetail(bookId);
		return ApiResponse.onSuccess(bookDetail);
	}

	@DeleteMapping("/{bookId}")
	public ApiResponse<?> deleteBook(@PathVariable Long bookId) {
		bookService.deleteBook(bookId);
		return ApiResponse.onSuccess(null);
	}

	@PatchMapping("/{bookId}")
	public ApiResponse<?> updateBookStatus(
		@PathVariable Long bookId,
		@RequestParam("readStatus") String readStatus) {
		bookService.updateBookStatus(bookId, readStatus); // 상태 업데이트 처리
		return ApiResponse.onSuccess("도서 상태가 '" + readStatus + "'로 변경되었습니다.");
	}

	@PostMapping("/{bookId}/share")
	public ApiResponse<?> shareBook(@PathVariable Long bookId,
		@RequestParam(name = "bookclubId") Long bookclubId) {
		bookService.shareBook(bookId, bookclubId);
		return ApiResponse.onSuccess("북클럽 공유하기 성공");
	}
}