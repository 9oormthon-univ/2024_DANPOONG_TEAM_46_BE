package com.goormthon.bookduchilseong.domain.book.service;

import java.util.List;

import com.goormthon.bookduchilseong.domain.book.dto.request.BookRequestDTO;
import com.goormthon.bookduchilseong.domain.book.dto.response.BookResponseDTO;

public interface BookService {
	BookResponseDTO addBook(BookRequestDTO requestDto, String token);

	List<BookResponseDTO> getAllBooks(String token);

	BookResponseDTO getBookDetail(Long bookId);

	void deleteBook(Long bookId);

	void updateBookStatus(Long bookId, String readStatus);

	void shareBook(Long bookId, Long bookclubId);
}