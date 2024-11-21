package com.goormthon.bookduchilseong.domain.zodiacsign.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.goormthon.bookduchilseong.domain.user.entity.User;
import com.goormthon.bookduchilseong.domain.zodiacsign.entity.Zodiacsign;

@Repository
public interface ZodiacsignRepository extends JpaRepository<Zodiacsign, Long> {
	List<Zodiacsign> findByUser(User user);

	Optional<Zodiacsign> findById(Long zodiacsignId);

	@Query("SELECT z FROM Zodiacsign z WHERE z.user = :user AND z.status = false")
	List<Zodiacsign> findByUserAndStatusFalse(User user);
}
