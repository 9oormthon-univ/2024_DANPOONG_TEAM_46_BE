package com.goormthon.bookduchilseong.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserMyPageReseponseDto {
    private int zodiacsignCnt; // 별자리 개수
    private int readingCnt;    // 독서 개수
    private int bookClubCnt;   // 책 모임 개수
    private List<MyBook> myBook; // 책 정보 리스트

    @Getter
    @AllArgsConstructor
    public static class MyBook{
        private String title;   // 책 제몬
        private String author;  // 저자
        private int startPoint; // 시작 페이지
        private int totalPage;  // 전체 페이지
        private int readPage;   // 현재 읽은 페이지
        private int progress;   // 진행도(퍼센트)
    }

}
