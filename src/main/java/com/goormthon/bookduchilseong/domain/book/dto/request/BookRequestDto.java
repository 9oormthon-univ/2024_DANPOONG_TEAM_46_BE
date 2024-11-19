package com.goormthon.bookduchilseong.domain.book.dto.request;

import com.goormthon.bookduchilseong.domain.book.entity.ReadStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequestDto {
	private Long userId;
	private String title;
	private String author;
	private Long totalPage;
	private Long goalDayPage;
	private ReadStatus status; // Enum 추가
}