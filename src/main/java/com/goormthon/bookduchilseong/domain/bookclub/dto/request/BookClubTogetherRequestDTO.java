package com.goormthon.bookduchilseong.domain.bookclub.dto.request;

import com.goormthon.bookduchilseong.domain.bookclub.entity.ReadType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookClubTogetherRequestDTO {
	private String title;
	private ReadType type;
	private String introduction;
	private String startDate;
	private String endDate;
	private int maxParticipant;
	private String profile;
	private String bookTitle;
	private String author;
	private int totalPage;
}
