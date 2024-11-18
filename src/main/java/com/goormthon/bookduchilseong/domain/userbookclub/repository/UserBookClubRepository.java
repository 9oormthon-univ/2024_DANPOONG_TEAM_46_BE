package com.goormthon.bookduchilseong.domain.userbookclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goormthon.bookduchilseong.domain.bookclub.entity.BookClub;
import com.goormthon.bookduchilseong.domain.bookclub.entity.User;
import com.goormthon.bookduchilseong.domain.userbookclub.entity.UserBookClub;

public interface UserBookClubRepository extends JpaRepository<UserBookClub, Long> {

	boolean existsByUserAndBookClub(User user, BookClub bookClub);
}
