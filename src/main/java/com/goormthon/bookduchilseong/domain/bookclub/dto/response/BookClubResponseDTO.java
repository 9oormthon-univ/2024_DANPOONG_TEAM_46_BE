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
public class BookClubResponseDTO {
	private Long id;
	private String title;
	private String bookTitle;
	private String introduction;
	private LocalDate startDate;
	private LocalDate endDate;
	private int participateCount;
	private int maxParticipant;
	private String profile;
}
