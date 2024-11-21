package com.goormthon.bookduchilseong.domain.certification.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertificationResponseDTO {
	private String message;

	public CertificationResponseDTO(String message) {
		this.message = message;
	}
}