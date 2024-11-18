package com.goormthon.bookduchilseong.domain.book.service;

import com.goormthon.bookduchilseong.domain.book.dto.request.BookRequestDto;
import com.goormthon.bookduchilseong.domain.book.dto.request.BookResponseDto;
import com.goormthon.bookduchilseong.domain.book.entity.ReadStatus;

import java.util.List;

public interface BookService {
    BookResponseDto addBook(BookRequestDto requestDto);
    List<BookResponseDto> getAllBooks(Long userId);
    BookResponseDto getBookDetail(Long bookId);
    void deleteBook(Long bookId);
    void updateBookStatus(Long bookId, String readStatus);
}
