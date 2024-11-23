package com.goormthon.bookduchilseong.domain.book.service;

import java.util.List;
import java.util.stream.Collectors;

import com.goormthon.bookduchilseong.global.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.Jwt;
import org.springframework.stereotype.Service;

import com.goormthon.bookduchilseong.domain.book.dto.request.BookRequestDTO;
import com.goormthon.bookduchilseong.domain.book.dto.response.BookResponseDTO;
import com.goormthon.bookduchilseong.domain.book.entity.Book;
import com.goormthon.bookduchilseong.domain.book.entity.ReadStatus;
import com.goormthon.bookduchilseong.domain.book.repository.BookRepository;
import com.goormthon.bookduchilseong.domain.bookclub.entity.BookClub;
import com.goormthon.bookduchilseong.domain.bookclub.repository.BookClubRepository;
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
	private final BookClubRepository bookClubRepository;
	private final JwtTokenProvider jwtTokenProvider;
	@Override
	public BookResponseDTO addBook(BookRequestDTO requestDto, String token) {
		Long userId = jwtTokenProvider.getUserIdFromToken(token);

		Book book = Book.builder()
			.user(findUserByUserId(token))
			.title(requestDto.getTitle())
			.author(requestDto.getAuthor())
			.totalPage(requestDto.getTotalPage())
			.goalDayPage(requestDto.getGoalDayPage())
			.readPage(0)
			.profile(requestDto.getProfile())
			.status(requestDto.getStatus()) // Enum 값 저장
			.build();
		bookRepository.save(book);
		return new BookResponseDTO(book);
	}

	@Override
	public List<BookResponseDTO> getAllBooks(String token) {
		Long userId = jwtTokenProvider.getUserIdFromToken(token);

		return bookRepository.findByUserId(userId).stream()
			.map(BookResponseDTO::new)
			.collect(Collectors.toList());
	}

	@Override
	public BookResponseDTO getBookDetail(Long bookId) {
		Book book = findBookById(bookId);

		return new BookResponseDTO(book);
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
			book.updateReadStatusStatus(status);
			bookRepository.save(book);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("유효하지 않은 도서 상태입니다: " + readStatus);
		}
	}

	@Override
	public void shareBook(Long bookId, Long bookclubId) {
		Book book = findBookById(bookId);

		BookClub bookClub = bookClubRepository.findById(bookclubId)
			.orElseThrow(() -> new GeneralException(ErrorStatus._BOOKCLUB_NOT_FOUND));

		book.updateBookclub(bookClub);

		bookRepository.save(book);
	}

	private User findUserByUserId(String token) {
		Long userId = jwtTokenProvider.getUserIdFromToken(token);
		return userRepository.findById(userId)
			.orElseThrow(() -> new GeneralException(ErrorStatus._USER_NOT_FOUND));
	}

	private Book findBookById(Long bookId) {
		return bookRepository.findById(bookId)
			.orElseThrow(() -> new GeneralException(ErrorStatus._BOOK_NOT_FOUND));
	}
}