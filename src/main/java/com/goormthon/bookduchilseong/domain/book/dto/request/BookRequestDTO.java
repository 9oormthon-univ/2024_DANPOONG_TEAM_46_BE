package com.goormthon.bookduchilseong.domain.book.dto.request;

import com.goormthon.bookduchilseong.domain.book.entity.ReadStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequestDTO {

	@NotNull(message = "User ID is required.")
	private Long userId;

	@NotBlank(message = "Title is required.")
	@Size(max = 255, message = "Title must be 255 characters or less.")
	private String title;

	@NotBlank(message = "Author is required.")
	@Size(max = 255, message = "Author name must be 255 characters or less.")
	private String author;

	@Positive(message = "Total page must be greater than 0.")
	private Integer totalPage;

	@Positive(message = "Goal day page must be greater than 0.")
	private Integer goalDayPage;

	@Pattern(regexp = "NOT_STARTED|IN_PROGRESS|COMPLETED", message = "Type must be NOT_STARTED|IN_PROGRESS|COMPLETED.")
	private ReadStatus status;

	@Size(max = 255, message = "Profile URL must not exceed 255 characters.")
	private String profile;
}