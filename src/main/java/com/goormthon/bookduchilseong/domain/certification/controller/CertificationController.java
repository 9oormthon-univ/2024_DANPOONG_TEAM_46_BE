package com.goormthon.bookduchilseong.domain.certification.controller;

import com.goormthon.bookduchilseong.domain.certification.dto.CertificationRequestDto;
import com.goormthon.bookduchilseong.domain.certification.service.CertificationService;
import com.goormthon.bookduchilseong.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books/{bookId}/certification")
@RequiredArgsConstructor
public class CertificationController {

    private final CertificationService certificationService;

    @PostMapping
    public ApiResponse<String> createCertification(
            @PathVariable Long bookId,
            @RequestBody CertificationRequestDto requestDto) {

        return certificationService.createCertification(bookId, requestDto);
    }

}
