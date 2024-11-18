package com.goormthon.bookduchilseong.domain.bookclub.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor()
@NoArgsConstructor
@Table(name = "book_club")
public class BookClub {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "book_title", nullable = true)
	private String bookTitle;

	@Column(name = "type")
	private ReadType type;

	@Column(name = "introduction", columnDefinition = "TEXT")
	private String introduction;

	@Column(name = "participate_count")
	private int participateCount;

	@Column(name = "start_date")
	private LocalDate startDate;

	@Column(name = "end_date")
	private LocalDate endDate;

	@Column(name = "maxParticipant")
	private int maxParticipant;

	@Column(name = "profile")
	private String profile;

	@Builder
	public BookClub(String title, String bookTitle, ReadType type, String introduction, LocalDate startDate,
		LocalDate endDate, int maxParticipant, String profile) {
		this.title = title;
		this.bookTitle = bookTitle;
		this.type = type;
		this.introduction = introduction;
		this.startDate = startDate;
		this.endDate = endDate;
		this.participateCount = 1;
		this.maxParticipant = maxParticipant;
		this.profile = profile;
	}

	public void increaseParticipateCount(int maxParticipant) {
		if (participateCount > maxParticipant) {
			throw new IllegalArgumentException("The number of participants has reached the maximum.");
		} else {
			this.participateCount = participateCount + 1;
		} 
	}

	public void decreaseParticipateCount() {
		this.participateCount = participateCount - 1;
	}
}
