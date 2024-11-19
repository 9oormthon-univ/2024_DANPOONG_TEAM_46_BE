package com.goormthon.bookduchilseong.domain.memo.repository;

import com.goormthon.bookduchilseong.domain.memo.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Long> {
}