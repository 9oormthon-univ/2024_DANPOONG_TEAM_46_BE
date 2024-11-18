package com.goormthon.bookduchilseong.domain.book.entity.book.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book")
public class Book {

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

    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createAt; // 생성일

    @Column(name = "update_at", nullable = false)
    private LocalDateTime updateAt; // 수정일

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted; // 삭제 여부

    @Builder
    public Book(Long userId, String title, String author, Long totalPage, Long goalDayPage, Long readPage,
                ReadStatus status, LocalDateTime createAt, LocalDateTime updateAt, Boolean isDeleted) {
        this.userId = userId;
        this.title = title;
        this.author = author;
        this.totalPage = totalPage;
        this.goalDayPage = goalDayPage;
        this.readPage = readPage;
        this.status = status;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.isDeleted = isDeleted;
    }
}
