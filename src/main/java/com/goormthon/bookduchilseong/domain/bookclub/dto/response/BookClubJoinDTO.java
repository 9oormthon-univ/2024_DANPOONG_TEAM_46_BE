package com.goormthon.bookduchilseong.domain.bookclub.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookClubJoinDTO {
	private Long id;
	private String title;
	private String bookTitle;
	private int maxParticipant;
	private int participateCount;
	private String profile;
	private LocalDate startDate;
	private LocalDate endDate;
}
