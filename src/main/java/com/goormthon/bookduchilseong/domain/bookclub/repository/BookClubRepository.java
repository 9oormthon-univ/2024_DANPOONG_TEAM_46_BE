package com.goormthon.bookduchilseong.domain.bookclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goormthon.bookduchilseong.domain.bookclub.entity.BookClub;

@Repository
public interface BookClubRepository extends JpaRepository<BookClub, Long> {
}
