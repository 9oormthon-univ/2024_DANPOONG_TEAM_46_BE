package com.goormthon.bookduchilseong.domain.book.dto.request;

import com.goormthon.bookduchilseong.domain.book.entity.Book;
import com.goormthon.bookduchilseong.domain.book.entity.ReadStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponseDto {
	private Long id;
	private String title;
	private String author;
	private int progress; // 독서 진행률
	private Integer totalPage;
	private Integer goalDayPage;
	private Integer readPage;
	private ReadStatus status; // Enum 추가

	public BookResponseDto(Book book) {
		this.id = book.getId();
		this.title = book.getTitle();
		this.author = book.getAuthor();
		this.progress = (int) ((double) book.getReadPage() / book.getTotalPage() * 100); // 진행률 계산
		this.totalPage = book.getTotalPage();
		this.goalDayPage = book.getGoalDayPage();
		this.readPage = book.getReadPage();
		this.status = book.getStatus();
	}
}