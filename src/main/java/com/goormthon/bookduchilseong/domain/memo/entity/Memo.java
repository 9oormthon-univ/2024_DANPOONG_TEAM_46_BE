package com.goormthon.bookduchilseong.domain.memo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "memo")
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;         // 메모 ID

    @Column(name = "bookId", nullable = false)
    private Long bookId;     // 도서 ID

    @Column(name = "image", nullable = false)
    private String image;    // 이미지 URL

    @Column(name = "content", nullable = false)
    private String content;  // 메모 내용
}
