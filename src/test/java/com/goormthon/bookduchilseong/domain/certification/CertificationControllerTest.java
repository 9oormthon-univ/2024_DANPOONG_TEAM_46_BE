package com.goormthon.bookduchilseong.domain.certification;

import com.goormthon.bookduchilseong.domain.certification.controller.CertificationController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormthon.bookduchilseong.domain.certification.dto.CertificationRequestDto;
import com.goormthon.bookduchilseong.domain.certification.service.CertificationService;
import com.goormthon.bookduchilseong.global.apiPayload.ApiResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CertificationController.class)
public class CertificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CertificationService certificationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createCertification_Success() throws Exception {
        // Given: 요청 데이터 설정
        CertificationRequestDto requestDto = new CertificationRequestDto();
        requestDto.setStartPage(53);
        requestDto.setEndPage(78);
        requestDto.setImage("base64Url");
        requestDto.setParagraph("인상 깊은 구절");

        // Mock: Service의 createCertification 동작 설정
        when(certificationService.createCertification(Mockito.eq(1L), Mockito.any(CertificationRequestDto.class)))
                .thenReturn(ApiResponse.onSuccess("도서 인증하기 성공"));

        // When: POST 요청 수행
        mockMvc.perform(post("/api/v1/books/1/certification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                // Then: 기대 결과 검증
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("okay"))
                .andExpect(jsonPath("$.result").value("도서 인증하기 성공"));
    }

    @Test
    void createCertification_Failure_BookNotFound() throws Exception {
        // Given: 요청 데이터 설정
        CertificationRequestDto requestDto = new CertificationRequestDto();
        requestDto.setStartPage(53);
        requestDto.setEndPage(78);
        requestDto.setImage("base64Url");
        requestDto.setParagraph("인상 깊은 구절");

        // Mock: Service에서 RuntimeException 발생 설정
        when(certificationService.createCertification(Mockito.eq(999L), Mockito.any(CertificationRequestDto.class)))
                .thenThrow(new RuntimeException("해당 도서를 찾을 수 없습니다."));

        // When: POST 요청 수행
        mockMvc.perform(post("/api/v1/books/999/certification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                // Then: 기대 결과 검증
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(false))
                .andExpect(jsonPath("$.code").value("500"))
                .andExpect(jsonPath("$.message").value("도서 인증 실패: 해당 도서를 찾을 수 없습니다."))
                .andExpect(jsonPath("$.result").doesNotExist());
    }
}