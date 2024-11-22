package com.goormthon.bookduchilseong.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goormthon.bookduchilseong.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
}
