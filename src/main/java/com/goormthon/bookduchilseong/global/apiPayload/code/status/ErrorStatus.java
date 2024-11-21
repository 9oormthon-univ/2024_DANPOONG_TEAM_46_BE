package com.goormthon.bookduchilseong.global.apiPayload.code.status;

import org.springframework.http.HttpStatus;

import com.goormthon.bookduchilseong.global.apiPayload.code.BaseErrorCode;
import com.goormthon.bookduchilseong.global.apiPayload.code.ErrorReasonDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

	_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON5000", "서버에러"),
	_BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON4000", "잘못된 요청"),
	_USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER4001", "사용자가 없습니다."),
	_BOOKCLUB_NOT_FOUND(HttpStatus.BAD_REQUEST, "BOOKCLUB4001", "해당 북클럽이 없습니다."),
	_BOOK_NOT_FOUND(HttpStatus.BAD_REQUEST, "BOOK4001", "해당 책이 없습니다."),
	_ZODIACSIGN_NOT_FOUND(HttpStatus.BAD_REQUEST, "ZODIACSIGN4001", "해당 별자리가 없습니다."),
	_DRAW_COUNT_ZERO(HttpStatus.BAD_REQUEST, "DRAW4001", "남은 별자리 뽑기 횟수가 없습니다.");


	private HttpStatus httpStatus;
	private String code;
	private String message;

	@Override
	public ErrorReasonDto getReason() {
		return ErrorReasonDto.builder().message(message).code(code).isSuccess(false).build();
	}

	@Override
	public ErrorReasonDto getReasonHttpStatus() {
		return ErrorReasonDto.builder().message(message).code(code).isSuccess(false).httpStatus(httpStatus).build();
	}
}
