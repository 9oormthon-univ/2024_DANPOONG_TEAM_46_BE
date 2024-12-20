package com.goormthon.bookduchilseong.domain.memo.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemoResponseDTO {
	private Long id;         // 메모 ID
	private Long bookId;     // 도서 ID
	private String image;    // 이미지 URL
	private String content;  // 메모 내용
}