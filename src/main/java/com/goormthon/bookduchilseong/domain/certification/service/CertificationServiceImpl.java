package com.goormthon.bookduchilseong.domain.certification.service;

import org.springframework.stereotype.Service;

import com.goormthon.bookduchilseong.domain.book.entity.Book;
import com.goormthon.bookduchilseong.domain.book.repository.BookRepository;
import com.goormthon.bookduchilseong.domain.certification.dto.CertificationRequestDto;
import com.goormthon.bookduchilseong.domain.certification.entity.Certification;
import com.goormthon.bookduchilseong.domain.certification.repository.CertificationRepository;
import com.goormthon.bookduchilseong.domain.user.entity.User;
import com.goormthon.bookduchilseong.domain.user.repository.UserRepository;
import com.goormthon.bookduchilseong.global.apiPayload.ApiResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CertificationServiceImpl implements CertificationService {

	private final CertificationRepository certificationRepository;
	private final BookRepository bookRepository;
	private final UserRepository userRepository;

	@Override
	public ApiResponse<String> createCertification(Long userId, Long bookId, CertificationRequestDto requestDto) {
		// Certification 엔티티 생성 및 저장
		Certification certification = Certification.builder()
			.book(findBookById(bookId))
			.startPage(requestDto.getStartPage())
			.endPage(requestDto.getEndPage())
			.image(requestDto.getImage())
			.paragraph(requestDto.getParagraph())
			.build();

		certificationRepository.save(certification);

		Book book = findBookById(bookId);
		book.setReadPage(requestDto.getEndPage());
		bookRepository.save(book);

		if (isCompleteBook(bookId)) {
			addDraw(userId);
		}

		// 성공 응답 반환
		return ApiResponse.onSuccess("도서 인증하기 성공");
	}

	private User findUserById(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("해당 사용자를 찾을 수 없습니다."));
	}

	private Book findBookById(Long bookId) {
		return bookRepository.findById(bookId)
			.orElseThrow(() -> new RuntimeException("해당 도서를 찾을 수 없습니다."));
	}

	private boolean isCompleteBook(Long bookId) {

		Book book = findBookById(bookId);

		if (book.getReadPage().equals(book.getTotalPage())) {
			return true;
		}
		return false;
	}

	private void addDraw(Long userId) {
		User user = findUserById(userId);
		user.addDraw();
		userRepository.save(user);
	}
}