package com.goormthon.bookduchilseong.domain.certification.repository;

import java.util.List;

import com.goormthon.bookduchilseong.domain.book.entity.Book;
import com.goormthon.bookduchilseong.domain.certification.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificationRepository extends JpaRepository<Certification, Long> {

	List<Certification>  findByBook(Book book);
}