package com.goormthon.bookduchilseong.domain.bookclub.dto.request;

import com.goormthon.bookduchilseong.domain.bookclub.entity.ReadType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookClubResqeustDTO {
	private String title;
	private String bookTitle;
	private ReadType type;
	private String introduction;
	private String startDate;
	private String endDate;
	private int maxParticipant;
	private String profile;

}
