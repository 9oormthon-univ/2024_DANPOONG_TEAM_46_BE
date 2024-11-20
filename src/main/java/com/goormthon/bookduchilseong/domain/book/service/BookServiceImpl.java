package com.goormthon.bookduchilseong.domain.book.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.goormthon.bookduchilseong.domain.book.dto.request.BookRequestDto;
import com.goormthon.bookduchilseong.domain.book.dto.request.BookResponseDto;
import com.goormthon.bookduchilseong.domain.book.entity.Book;
import com.goormthon.bookduchilseong.domain.book.entity.ReadStatus;
import com.goormthon.bookduchilseong.domain.book.repository.BookRepository;
import com.goormthon.bookduchilseong.domain.user.entity.User;
import com.goormthon.bookduchilseong.domain.user.repository.UserRepository;
import com.goormthon.bookduchilseong.global.apiPayload.code.status.ErrorStatus;
import com.goormthon.bookduchilseong.global.apiPayload.exception.GeneralException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;
	private final UserRepository userRepository;

	@Override
	public BookResponseDto addBook(BookRequestDto requestDto) {
		Book book = Book.builder()
			.user(findUserByUserId(requestDto.getUserId()))
			.title(requestDto.getTitle())
			.author(requestDto.getAuthor())
			.totalPage(requestDto.getTotalPage())
			.goalDayPage(requestDto.getGoalDayPage())
			.readPage(0)
			.profile(requestDto.getProfile())
			.status(requestDto.getStatus()) // Enum 값 저장
			.build();
		bookRepository.save(book);
		return new BookResponseDto(book);
	}

	@Override
	public List<BookResponseDto> getAllBooks(Long userId) {
		User user = findUserByUserId(userId);

		return bookRepository.findByUserId(userId).stream()
			.map(BookResponseDto::new)
			.collect(Collectors.toList());
	}

	@Override
	public BookResponseDto getBookDetail(Long bookId) {
		Book book = findBookById(bookId);

		return new BookResponseDto(book);
	}

	@Override
	public void deleteBook(Long bookId) {
		// Book을 ID로 조회, 없으면 예외 발생
		Book book = findBookById(bookId);

		// isDeleted를 true로 설정하여 소프트 삭제 처리
		book.setIsDeleted(true);

		// 변경 사항 저장
		bookRepository.save(book);
	}

	@Override
	@Transactional
	public void updateBookStatus(Long bookId, String readStatus) {
		// Book 조회
		Book book = findBookById(bookId);

		// Enum으로 상태 업데이트
		try {
			ReadStatus status = ReadStatus.valueOf(readStatus.toUpperCase());
			book.setStatus(status);
			bookRepository.save(book);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("유효하지 않은 도서 상태입니다: " + readStatus);
		}
	}

	private User findUserByUserId(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new GeneralException(ErrorStatus._USER_NOT_FOUND));
	}

	private Book findBookById(Long bookId) {
		return bookRepository.findById(bookId)
			.orElseThrow(() -> new GeneralException(ErrorStatus._BOOK_NOT_FOUND));
	}
}