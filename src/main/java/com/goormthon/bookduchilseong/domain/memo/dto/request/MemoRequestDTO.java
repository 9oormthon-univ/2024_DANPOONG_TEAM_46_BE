package com.goormthon.bookduchilseong.domain.memo.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemoRequestDTO {

	@NotNull(message = "Book ID is required.")
	private Long bookId;

	@NotBlank(message = "Image URL is required.")
	@Size(max = 255, message = "Image URL must be 255 characters or less.")
	private String image;

	@NotBlank(message = "Content is required.")
	@Size(max = 1000, message = "Content must be 1000 characters or less.")
	private String content;
}