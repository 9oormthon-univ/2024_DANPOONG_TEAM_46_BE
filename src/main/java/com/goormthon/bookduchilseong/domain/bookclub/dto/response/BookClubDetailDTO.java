package com.goormthon.bookduchilseong.domain.bookclub.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookClubDetailDTO {
	private Long id;
	private String ownerName;
	private String bookTitle;
	private String startDate;
	private String endDate;
	private int participateCount;
	private int maxParticipant;
	private String profile;

}
