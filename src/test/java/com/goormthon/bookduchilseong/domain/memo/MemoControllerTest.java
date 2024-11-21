package com.goormthon.bookduchilseong.domain.memo;

import com.goormthon.bookduchilseong.domain.memo.controller.MemoController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormthon.bookduchilseong.domain.memo.dto.request.MemoRequestDTO;
import com.goormthon.bookduchilseong.domain.memo.service.MemoService;
import com.goormthon.bookduchilseong.global.apiPayload.ApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemoController.class)
public class MemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemoService memoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createMemo_Success() throws Exception {
        // Given
        MemoRequestDTO requestDto = new MemoRequestDTO();
        requestDto.setBookId(1L);
        requestDto.setImage("base64ImageURL");
        requestDto.setContent("오늘의 메모");

        ApiResponse<String> response = ApiResponse.onSuccess("메모 추가 성공");

        // Mock
        doReturn(response).when(memoService).createMemo(any(MemoRequestDTO.class));

        // When
        mockMvc.perform(post("/api/v1/memos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("okay"))
                .andExpect(jsonPath("$.result").value("메모 추가 성공"));
    }

    @Test
    void createMemo_Failure() throws Exception {
        // Given: 요청 데이터 생성
        MemoRequestDTO requestDto = new MemoRequestDTO();
        requestDto.setBookId(1L);
        requestDto.setImage("base64ImageURL");
        requestDto.setContent("오늘의 메모");

        // Mock: 서비스 레이어에서 예외 발생 설정
        doThrow(new RuntimeException("메모 추가 실패")).when(memoService).createMemo(any(MemoRequestDTO.class));

        // When: POST 요청 수행
        mockMvc.perform(post("/api/v1/memos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                // Then: 응답 값 검증
                //.andExpect(status().isOk()) // 여기서는 상태 코드가 200으로 설정되어 있음
                .andExpect(jsonPath("$.isSuccess").value(false))
                .andExpect(jsonPath("$.code").value("500"))
                .andExpect(jsonPath("$.message").value("메모 생성 실패: 메모 추가 실패"))
                .andExpect(jsonPath("$.result").doesNotExist());
    }
}