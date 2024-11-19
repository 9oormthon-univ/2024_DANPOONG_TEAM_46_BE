package com.goormthon.bookduchilseong.domain.memo.entity;

import com.goormthon.bookduchilseong.domain.book.entity.Book;
import com.goormthon.bookduchilseong.global.common.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "memo")
public class Memo extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;         // 메모 ID

	@JoinColumn(name = "book_id", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Book book; // 도서 ID

	@Column(name = "image", nullable = false)
	private String image;    // 이미지 URL

	@Column(name = "content", nullable = false)
	private String content;  // 메모 내용
}