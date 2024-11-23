package com.goormthon.bookduchilseong.domain.certification.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.goormthon.bookduchilseong.domain.book.entity.Book;
import com.goormthon.bookduchilseong.domain.book.repository.BookRepository;
import com.goormthon.bookduchilseong.domain.certification.dto.request.CertificationRequestDTO;
import com.goormthon.bookduchilseong.domain.certification.entity.Certification;
import com.goormthon.bookduchilseong.domain.certification.repository.CertificationRepository;
import com.goormthon.bookduchilseong.domain.gemini.service.GeminiService;
import com.goormthon.bookduchilseong.domain.ocr.service.OCRService;
import com.goormthon.bookduchilseong.domain.user.entity.User;
import com.goormthon.bookduchilseong.domain.user.repository.UserRepository;
import com.goormthon.bookduchilseong.global.apiPayload.ApiResponse;
import com.goormthon.bookduchilseong.global.apiPayload.code.status.ErrorStatus;
import com.goormthon.bookduchilseong.global.apiPayload.exception.GeneralException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CertificationServiceImpl implements CertificationService {

	private final CertificationRepository certificationRepository;
	private final BookRepository bookRepository;
	private final UserRepository userRepository;
	private final OCRService ocrService;
	private final GeminiService geminiService;

	@Override
	public ApiResponse<String> createCertification(Long userId, Long bookId, CertificationRequestDTO requestDto) {
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
		book.updateReadPage(requestDto.getEndPage());
		bookRepository.save(book);

		if (isCompleteBook(bookId)) {
			addDraw(userId);
		}

		// 성공 응답 반환
		return ApiResponse.onSuccess("도서 인증하기 성공");
	}

	@Override
	public String createSummation(Long certficationId, MultipartFile image) {

		Certification certification = findCertificationById(certficationId);

		String imageText = ocrService.execute(image);

		if (imageText == null) {
			throw new GeneralException(ErrorStatus._IMAGE_TEXT_NOT_CREATE);
		}

		String summation = geminiService.getContents(imageText);

		if (summation == null) {
			throw new GeneralException(ErrorStatus._SUMMATION_NOT_CREATE);
		}

		certification.createSummation(summation);

		certificationRepository.save(certification);

		return summation;
	}

	private User findUserById(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new GeneralException(ErrorStatus._USER_NOT_FOUND));
	}

	private Book findBookById(Long bookId) {
		return bookRepository.findById(bookId)
			.orElseThrow(() -> new GeneralException(ErrorStatus._BOOK_NOT_FOUND));
	}

	private Certification findCertificationById(Long certficationId) {
		return certificationRepository.findById(certficationId)
			.orElseThrow(() -> new GeneralException(ErrorStatus._CERTIFICATION_NOT_FOUND));
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