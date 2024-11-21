package com.goormthon.bookduchilseong.domain.bookclub.dto.request;

import com.goormthon.bookduchilseong.domain.bookclub.entity.ReadType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookClubTogetherRequestDTO {

	@NotNull(message = "Title is required.")
	@Size(min = 1, max = 50, message = "Title must be between 1 and 50 characters.")
	private String title;

	@Schema(defaultValue = "TOGETHER")
	private ReadType type;

	@Size(max = 500, message = "Introduction must be 500 characters or less.")
	private String introduction;

	@Pattern(regexp = "2024-11-23", message = "Start date must be in the format YYYY-MM-DD.")
	private String startDate;

	@Pattern(regexp = "2024-11-23", message = "End date must be in the format YYYY-MM-DD.")
	private String endDate;

	@Min(value = 1, message = "Maximum participants must be at least 1.")
	@Max(value = 10, message = "Maximum participants cannot exceed 10.")
	private int maxParticipant;

	@Size(max = 255, message = "Profile URL must not exceed 255 characters.")
	private String profile;

	@NotBlank(message = "booktitle is required.")
	@Size(max = 255, message = "Title must be 255 characters or less.")
	private String bookTitle;

	@NotBlank(message = "Author is required.")
	@Size(max = 255, message = "Author name must be 255 characters or less.")
	private String author;

	@Positive(message = "Total page must be greater than 0.")
	private Integer totalPage;
}