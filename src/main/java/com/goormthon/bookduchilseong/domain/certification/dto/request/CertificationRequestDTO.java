package com.goormthon.bookduchilseong.domain.certification.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CertificationRequestDTO {

	@Positive(message = "Start page must be greater than 0.")
	private Integer startPage;

	@Positive(message = "End page must be greater than 0.")
	private Integer endPage;

	@NotBlank(message = "Image URL is required.")
	@Size(max = 255, message = "Image URL must be 255 characters or less.")
	private String image;

	@NotBlank(message = "Paragraph is required.")
	@Size(max = 1000, message = "Paragraph must be 1000 characters or less.")
	private String paragraph;
}