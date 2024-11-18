package com.goormthon.bookduchilseong.domain.book.entity.book.dto.request.book.controller;

import com.goormthon.bookduchilseong.domain.book.entity.book.dto.request.book.dto.request.BookRequestDto;
import com.goormthon.bookduchilseong.domain.book.entity.book.dto.request.book.dto.request.BookResponseDto;
import com.goormthon.bookduchilseong.domain.book.entity.book.dto.request.book.service.BookService;
import com.goormthon.bookduchilseong.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j // Lombok의 Slf4j 어노테이션 추가
@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> addBook(@RequestBody BookRequestDto requestDto) {
        BookResponseDto responseDto = bookService.addBook(requestDto);
        return ResponseEntity.ok(ApiResponse.onSuccess(responseDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllBooks(@RequestParam Long userId) {
        List<BookResponseDto> books = bookService.getAllBooks(userId);
        return ResponseEntity.ok(ApiResponse.onSuccess(books));
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<ApiResponse<?>> getBookDetail(@PathVariable Long bookId) {
        try{
            BookResponseDto bookDetail = bookService.getBookDetail(bookId);
            return ResponseEntity.ok(ApiResponse.onSuccess(bookDetail));
        }catch (RuntimeException e){
            return ResponseEntity.status(404)
                    .body(ApiResponse.onFailure("404", e.getMessage(), null));
        }
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<ApiResponse<?>> deleteBook(@PathVariable Long bookId) {
        try {
            bookService.deleteBook(bookId);
            return ResponseEntity.ok(ApiResponse.onSuccess(null));
        } catch (RuntimeException e) { // 책이 없을 때 발생하는 예외 처리
            return ResponseEntity.status(404) // 404 상태 코드 반환
                    .body(ApiResponse.onFailure("404", e.getMessage(), null));
        }
    }

    @PatchMapping("/{bookId}")
    public ApiResponse<String> updateBookStatus(
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
