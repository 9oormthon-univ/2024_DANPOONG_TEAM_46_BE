package com.goormthon.bookduchilseong.domain.certification.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CertificationRequestDTO {
	private Integer startPage;
	private Integer endPage;
	private String image;
	private String paragraph;
}