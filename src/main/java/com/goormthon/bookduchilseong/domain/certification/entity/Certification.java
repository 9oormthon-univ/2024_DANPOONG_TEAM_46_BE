package com.goormthon.bookduchilseong.domain.certification.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "certification")
public class Certification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 인증 ID

    @Column(name = "book_id", nullable = false)
    private Long bookId; // 도서 ID

    @Column(name = "start_page", nullable = false)
    private Integer startPage; // 시작 페이지

    @Column(name = "end_page", nullable = false)
    private Integer endPage; // 종료 페이지

    @Column(name = "image", nullable = false)
    private String image; // 인증 이미지 (Base64)

    @Column(name = "paragraph", nullable = false, columnDefinition = "TEXT")
    private String paragraph; // 인상 깊은 구절
}
