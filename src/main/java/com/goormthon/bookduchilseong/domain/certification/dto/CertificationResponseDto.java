package com.goormthon.bookduchilseong.domain.certification.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertificationResponseDto {
	private String message;

	public CertificationResponseDto(String message) {
		this.message = message;
	}
}