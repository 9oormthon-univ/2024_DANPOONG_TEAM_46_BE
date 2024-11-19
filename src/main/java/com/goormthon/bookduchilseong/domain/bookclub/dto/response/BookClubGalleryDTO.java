package com.goormthon.bookduchilseong.domain.bookclub.dto.response;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookClubGalleryDTO {
	private LocalDate date;
	private List<String> image;
}
