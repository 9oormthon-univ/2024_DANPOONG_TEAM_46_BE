package com.goormthon.bookduchilseong.domain.book.service;

import java.util.List;

import com.goormthon.bookduchilseong.domain.book.dto.request.BookRequestDTO;
import com.goormthon.bookduchilseong.domain.book.dto.response.BookResponseDTO;

public interface BookService {
	BookResponseDTO addBook(BookRequestDTO requestDto);

	List<BookResponseDTO> getAllBooks(Long userId);

	BookResponseDTO getBookDetail(Long bookId);

	void deleteBook(Long bookId);

	void updateBookStatus(Long bookId, String readStatus);
}