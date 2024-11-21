package com.goormthon.bookduchilseong.domain.certification.service;

import com.goormthon.bookduchilseong.domain.book.entity.Book;
import com.goormthon.bookduchilseong.domain.book.repository.BookRepository;
import com.goormthon.bookduchilseong.domain.certification.dto.request.CertificationRequestDTO;
import com.goormthon.bookduchilseong.domain.certification.entity.Certification;
import com.goormthon.bookduchilseong.domain.certification.repository.CertificationRepository;
import com.goormthon.bookduchilseong.global.apiPayload.ApiResponse;
import com.goormthon.bookduchilseong.global.apiPayload.code.status.ErrorStatus;
import com.goormthon.bookduchilseong.global.apiPayload.exception.GeneralException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CertificationServiceImpl implements CertificationService {

	private final CertificationRepository certificationRepository;
	private final BookRepository bookRepository;

	@Override
	public ApiResponse<String> createCertification(Long bookId, CertificationRequestDTO requestDto) {
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

		// 성공 응답 반환
		return ApiResponse.onSuccess("도서 인증하기 성공");
	}

	private Book findBookById(Long bookId) {
		return bookRepository.findById(bookId)
			.orElseThrow(() -> new GeneralException(ErrorStatus._BOOK_NOT_FOUND));
	}
}