package com.goormthon.bookduchilseong.domain.userbookclub.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goormthon.bookduchilseong.domain.bookclub.entity.BookClub;
import com.goormthon.bookduchilseong.domain.user.entity.User;
import com.goormthon.bookduchilseong.domain.userbookclub.entity.UserBookClub;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserBookClubRepository extends JpaRepository<UserBookClub, Long> {

	boolean existsByUserAndBookClub(User user, BookClub bookClub);

	Optional<UserBookClub> findByBookClubAndIsOwner(BookClub bookClub, boolean isOwner);

	List<UserBookClub> findByBookClub(BookClub bookClub);

	// 특정 사용자가 참여 중인 북클럽 목록 가져오기
	@Query("SELECT ubc.bookClub FROM UserBookClub ubc WHERE ubc.user = :user")
	List<BookClub> findByUser(@Param("user") User user);
}
