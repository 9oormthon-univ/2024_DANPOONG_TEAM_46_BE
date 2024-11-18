package com.goormthon.bookduchilseong.domain.certification.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormthon.bookduchilseong.domain.certification.dto.CertificationRequestDto;
import com.goormthon.bookduchilseong.domain.certification.service.CertificationService;
import com.goormthon.bookduchilseong.global.apiPayload.ApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CertificationController.class)
class CertificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CertificationService certificationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("도서 인증하기 성공")
    void createCertification_Success() throws Exception {
        // given
        Long bookId = 1L;
        CertificationRequestDto requestDto = new CertificationRequestDto();
        requestDto.setStartPage(10);
        requestDto.setEndPage(20);
        requestDto.setImage("base64Image");
        requestDto.setParagraph("인상 깊은 구절");

        ApiResponse<String> apiResponse = ApiResponse.onSuccess("도서 인증하기 성공");

        Mockito.when(certificationService.createCertification(eq(bookId), any(CertificationRequestDto.class)))
                .thenReturn(apiResponse);

        // when & then
        mockMvc.perform(post("/api/v1/books/{bookId}/certification", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("okay"))
                .andExpect(jsonPath("$.result").value("도서 인증하기 성공"));
    }
}
