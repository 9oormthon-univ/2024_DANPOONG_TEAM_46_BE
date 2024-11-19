package com.goormthon.bookduchilseong.domain.bookclub.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.goormthon.bookduchilseong.domain.bookclub.dto.response.BookClubResponseDTO;
import com.goormthon.bookduchilseong.domain.bookclub.entity.BookClub;
import com.goormthon.bookduchilseong.domain.user.entity.User;

@Repository
public interface BookClubRepository extends JpaRepository<BookClub, Long> {

	@Query("SELECT new com.goormthon.bookduchilseong.domain.bookclub.dto.response.BookClubResponseDTO(b.id, b.title"
		+ ", b.introduction, b.startDate, b.endDate, b.participateCount, b.maxParticipant, b.profile)"
		+ " FROM BookClub b")
	List<BookClubResponseDTO> findBookClubs(Pageable pageable);

	Optional<BookClub> findById(Long id);

	@Query("SELECT ubc.bookClub FROM UserBookClub ubc WHERE ubc.user = :user")
	List<BookClub> findByUser(User user);
}
