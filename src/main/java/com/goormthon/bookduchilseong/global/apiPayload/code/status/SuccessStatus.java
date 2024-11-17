package com.goormthon.bookduchilseong.global.apiPayload.code.status;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessStatus {
	_OK(HttpStatus.OK, "200", "okay");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
