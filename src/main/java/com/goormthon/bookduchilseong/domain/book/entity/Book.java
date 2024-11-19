package com.goormthon.bookduchilseong.domain.book.entity;

import com.goormthon.bookduchilseong.domain.bookclub.entity.BookClub;
import com.goormthon.bookduchilseong.domain.user.entity.User;
import com.goormthon.bookduchilseong.global.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book")
public class Book extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // 도서 ID

	@Column(name = "user_id", nullable = false)
	private Long userId; // 유저 ID

	@Column(name = "title", nullable = false)
	private String title; // 책 제목

	@Column(name = "author", nullable = false)
	private String author; // 작가

	@Column(name = "total_page", nullable = false)
	private Long totalPage; // 전체 페이지 수

	@Column(name = "goal_day_page", nullable = false)
	private Long goalDayPage; // 목표 하루 페이지 수

	@Column(name = "read_page", nullable = false)
	private Long readPage; // 읽은 페이지 수

	@Enumerated(EnumType.STRING) // Enum을 문자열로 저장
	@Column(name = "status", nullable = false)
	private ReadStatus status; // 독서 상태

	@Column(name = "profile", nullable = false)
	private String profile; // 책 이미지 URL

	@JoinColumn(name = "book_club_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private BookClub bookClub;

	@JoinColumn(name = "user_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@Builder
	public Book(Long userId, String title, String author, Long totalPage, Long goalDayPage, Long readPage,
		ReadStatus status, String profile, BookClub bookClub, User user) {
		this.userId = userId;
		this.title = title;
		this.author = author;
		this.totalPage = totalPage;
		this.goalDayPage = goalDayPage;
		this.readPage = readPage;
		this.status = status;
		this.profile = profile;
		this.bookClub = bookClub;
		this.user = user;
	}
}