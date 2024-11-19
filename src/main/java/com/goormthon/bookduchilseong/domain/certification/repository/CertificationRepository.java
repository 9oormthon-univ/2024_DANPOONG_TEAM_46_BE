package com.goormthon.bookduchilseong.domain.certification.repository;

import com.goormthon.bookduchilseong.domain.certification.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificationRepository extends JpaRepository<Certification, Long> {
}
