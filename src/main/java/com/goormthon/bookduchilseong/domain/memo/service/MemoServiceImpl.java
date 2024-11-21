package com.goormthon.bookduchilseong.domain.memo.service;

import org.springframework.stereotype.Service;

import com.goormthon.bookduchilseong.domain.book.entity.Book;
import com.goormthon.bookduchilseong.domain.book.repository.BookRepository;
import com.goormthon.bookduchilseong.domain.memo.dto.request.MemoRequestDTO;
import com.goormthon.bookduchilseong.domain.memo.entity.Memo;
import com.goormthon.bookduchilseong.domain.memo.repository.MemoRepository;
import com.goormthon.bookduchilseong.global.apiPayload.ApiResponse;
import com.goormthon.bookduchilseong.global.apiPayload.code.status.ErrorStatus;
import com.goormthon.bookduchilseong.global.apiPayload.exception.GeneralException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemoServiceImpl implements MemoService {

	private final MemoRepository memoRepository;
	private final BookRepository bookRepository;

	@Override
	public ApiResponse<?> createMemo(MemoRequestDTO requestDto) {
		// Certification 엔티티 생성 및 저장
		Memo memo = Memo.builder()
			.book(findBookById(requestDto.getBookId()))
			.image(requestDto.getImage())
			.content(requestDto.getContent())
			.build();

		// Repository를 통해 Memo 저장
		memoRepository.save(memo);

		// 성공 응답 반환
		return ApiResponse.onSuccess("메모 추가 성공");
	}

	private Book findBookById(Long bookId) {
		return bookRepository.findById(bookId)
			.orElseThrow(() -> new GeneralException(ErrorStatus._BOOK_NOT_FOUND));
	}
}