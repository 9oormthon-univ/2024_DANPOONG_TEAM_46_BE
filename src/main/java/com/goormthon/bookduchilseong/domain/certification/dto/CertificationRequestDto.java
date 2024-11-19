package com.goormthon.bookduchilseong.domain.certification.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CertificationRequestDto {
	private Integer startPage;
	private Integer endPage;
	private String image;
	private String paragraph;
}