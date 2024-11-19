package com.goormthon.bookduchilseong.domain.book.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goormthon.bookduchilseong.domain.book.entity.Book;
import com.goormthon.bookduchilseong.domain.bookclub.entity.BookClub;
import com.goormthon.bookduchilseong.domain.user.entity.User;

public interface BookRepository extends JpaRepository<Book, Long> {

	List<Book> findByUserId(Long userId);

	Optional<Book> findByBookClubAndUserId(Long bookClubId, Long userId);

	Optional<Book> findByBookClub(BookClub bookClub);

	Book findByBookClubAndUser(BookClub bookClub, User user);

	List<Book> findBooksByBookClub(BookClub bookClub);
}