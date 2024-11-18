package com.goormthon.bookduchilseong.domain.book.entity.book.repository;

import com.goormthon.bookduchilseong.domain.book.entity.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByUserId(Long userId);
}
