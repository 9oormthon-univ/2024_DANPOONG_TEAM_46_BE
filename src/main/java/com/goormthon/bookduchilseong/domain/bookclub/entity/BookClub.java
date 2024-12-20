package com.goormthon.bookduchilseong.domain.bookclub.entity;

import java.time.LocalDate;

import com.goormthon.bookduchilseong.global.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "book_club")
public class BookClub extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "type", nullable = false)
	private ReadType type;

	@Column(name = "introduction", columnDefinition = "TEXT", nullable = false)
	private String introduction;

	@Column(name = "participate_count", nullable = false)
	private int participateCount;

	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;

	@Column(name = "maxParticipant", nullable = false)
	private int maxParticipant;

	@Column(name = "profile", nullable = false)
	private String profile;

	@Builder
	public BookClub(String title, ReadType type, String introduction, LocalDate startDate, LocalDate endDate,
		int maxParticipant, String profile) {
		this.title = title;
		this.type = type;
		this.introduction = introduction;
		this.startDate = startDate;
		this.endDate = endDate;
		this.participateCount = 1;
		this.maxParticipant = maxParticipant != 0 ? maxParticipant : 1;
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
