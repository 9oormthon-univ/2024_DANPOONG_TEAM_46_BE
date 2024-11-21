package com.goormthon.bookduchilseong.domain.memo.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemoRequestDTO {
	private Long bookId;     // 도서 ID
	private String image;    // 이미지 URL
	private String content;  // 메모 내용
}