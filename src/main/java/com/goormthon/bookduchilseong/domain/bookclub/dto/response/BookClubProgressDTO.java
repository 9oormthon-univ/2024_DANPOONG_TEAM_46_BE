package com.goormthon.bookduchilseong.domain.bookclub.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookClubProgressDTO {
	private String name;
	private String zodiacsigns;
	private String profile;
	private int totalPage;
	private int readPage;
	private int goalDayPage;
}