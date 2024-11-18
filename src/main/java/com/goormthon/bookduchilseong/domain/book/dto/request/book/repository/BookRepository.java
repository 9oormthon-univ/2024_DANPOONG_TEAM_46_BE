package com.goormthon.bookduchilseong.domain.book.dto.request.book.repository;

import com.goormthon.bookduchilseong.domain.book.dto.request.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByUserId(Long userId);
}
