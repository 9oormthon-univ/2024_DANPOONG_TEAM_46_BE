package com.goormthon.bookduchilseong.domain.certification.entity;

import com.goormthon.bookduchilseong.domain.book.entity.Book;
import com.goormthon.bookduchilseong.global.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Builder
@Table(name = "certification")
public class Certification extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // 인증 ID

	@JoinColumn(name = "book_id", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Book book; // 도서 ID

	@Column(name = "start_page", nullable = false)
	private Integer startPage; // 시작 페이지

	@Column(name = "end_page", nullable = false)
	private Integer endPage; // 종료 페이지

	@Column(name = "image", nullable = false)
	private String image; // 인증 이미지 (Base64)

	@Column(name = "paragraph", nullable = false, columnDefinition = "TEXT")
	private String paragraph; // 인상 깊은 구절
}