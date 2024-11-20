package com.goormthon.bookduchilseong.domain.zodiacsign.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goormthon.bookduchilseong.domain.user.entity.User;
import com.goormthon.bookduchilseong.domain.zodiacsign.entity.Zodiacsign;

@Repository
public interface ZodiacsignRepository extends JpaRepository<Zodiacsign, Long> {
	List<Zodiacsign> findByUser(User user);

	int countByUserId(Long userId);

	Optional<Zodiacsign> findById(Long zodiacsignId);
}
