package com.goormthon.bookduchilseong.global.apiPayload.code.status;

import org.springframework.http.HttpStatus;

import com.goormthon.bookduchilseong.global.apiPayload.code.BaseErrorCode;
import com.goormthon.bookduchilseong.global.apiPayload.code.ErrorReasonDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

	_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON5000", "서버에러");

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
