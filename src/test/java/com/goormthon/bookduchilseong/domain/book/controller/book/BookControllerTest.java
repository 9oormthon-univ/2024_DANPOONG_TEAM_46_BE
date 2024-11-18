package com.goormthon.bookduchilseong.domain.book.controller.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormthon.bookduchilseong.domain.book.controller.BookController;
import com.goormthon.bookduchilseong.domain.book.dto.request.BookRequestDto;
import com.goormthon.bookduchilseong.domain.book.dto.request.BookResponseDto;
import com.goormthon.bookduchilseong.domain.book.entity.ReadStatus;
import com.goormthon.bookduchilseong.domain.book.service.BookService;
import com.goormthon.bookduchilseong.global.apiPayload.ApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addBookTest() throws Exception {
        // Mock 요청 데이터
        BookRequestDto requestDto = new BookRequestDto();
        requestDto.setUserId(1L);
        requestDto.setTitle("채식주의자");
        requestDto.setAuthor("한강");
        requestDto.setTotalPage(200L);
        requestDto.setGoalDayPage(20L);

        // Mock 응답 데이터
        BookResponseDto responseDto = new BookResponseDto();
        responseDto.setTitle("채식주의자");
        responseDto.setAuthor("한강");
        responseDto.setProgress(0);
        responseDto.setReadPage(0L);
        responseDto.setStatus(ReadStatus.NOT_STARTED);

        // Mock 동작 정의
        when(bookService.addBook(any(BookRequestDto.class))).thenReturn(responseDto);

        // 테스트 수행
        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"userId\":1,\"title\":\"채식주의자\",\"author\":\"한강\",\"totalPage\":200,\"goalDayPage\":20}"))
                    .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.result.title").value("채식주의자"));


        // Mock 동작 검증
        verify(bookService, times(1)).addBook(any(BookRequestDto.class));
    }

    @Test
    void getAllBooksTest() throws Exception {
        // Mock 데이터 준비
        BookResponseDto book1 = new BookResponseDto();
        book1.setTitle("채식주의자");
        book1.setAuthor("한강");
        book1.setProgress(50);
        book1.setReadPage(100L);
        book1.setStatus(ReadStatus.IN_PROGRESS);

        BookResponseDto book2 = new BookResponseDto();
        book2.setTitle("데미안");
        book2.setAuthor("헤르만 헤세");
        book2.setProgress(100);
        book2.setReadPage(200L);
        book2.setStatus(ReadStatus.COMPLETED);

        List<BookResponseDto> mockBooks = List.of(book1, book2);

        // Mock 동작 정의
        when(bookService.getAllBooks(1L)).thenReturn(mockBooks);

        // 테스트 수행
        mockMvc.perform(get("/api/v1/books")
                        .param("userId", "1") // @RequestParam 값 전달
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 기대 상태 코드
                .andExpect(jsonPath("$.isSuccess").value(true)) // 성공 여부 확인
                .andExpect(jsonPath("$.result[0].title").value("채식주의자")) // 첫 번째 책 제목 확인
                .andExpect(jsonPath("$.result[1].title").value("데미안")); // 두 번째 책 제목 확인

        // Mock 동작 검증
        verify(bookService, times(1)).getAllBooks(1L);
    }
    @Test
    void getBookDetail_Success() throws Exception {
        // Mock 데이터 준비
        BookResponseDto bookDetail = new BookResponseDto();
        bookDetail.setTitle("채식주의자");
        bookDetail.setAuthor("한강");
        bookDetail.setProgress(50);
        bookDetail.setReadPage(100L);
        bookDetail.setStatus(ReadStatus.IN_PROGRESS);

        // Mock 동작 정의
        when(bookService.getBookDetail(1L)).thenReturn(bookDetail);

        // 테스트 수행
        mockMvc.perform(get("/api/v1/books/1") // bookId = 1
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 상태 코드 200
                .andExpect(jsonPath("$.isSuccess").value(true)) // 성공 여부
                .andExpect(jsonPath("$.result.title").value("채식주의자")) // 책 제목 확인
                .andExpect(jsonPath("$.result.author").value("한강")) // 책 작가 확인
                .andExpect(jsonPath("$.result.progress").value(50)); // 진행률 확인

        // Mock 동작 검증
        verify(bookService, times(1)).getBookDetail(1L);
    }

    @Test
    void getBookDetail_NotFound() throws Exception {
        // Mock 동작 정의: 존재하지 않는 bookId 요청 시 예외 발생
        when(bookService.getBookDetail(999L)).thenThrow(new RuntimeException("책을 찾을 수 없습니다."));

        // 테스트 수행
        mockMvc.perform(get("/api/v1/books/999") // bookId = 999
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()) // 상태 코드 404
                .andExpect(jsonPath("$.isSuccess").value(false)) // 실패 여부
                .andExpect(jsonPath("$.code").value("404")) // 실패 코드 확인
                .andExpect(jsonPath("$.message").value("책을 찾을 수 없습니다.")); // 실패 메시지 확인

        // Mock 동작 검증
        verify(bookService, times(1)).getBookDetail(999L);
    }

    @Test
    void deleteBook_Success() throws Exception {
        // Mock 동작 정의
        doNothing().when(bookService).deleteBook(1L); // bookId = 1

        // 테스트 수행
        ResultActions result = mockMvc.perform(delete("/api/v1/books/1") // bookId = 1
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 상태 코드 200 확인
                .andExpect(jsonPath("$.isSuccess").value(true)) // 성공 여부 확인
                .andExpect(jsonPath("$.result").doesNotExist());; // result는 null

        // 응답 Body 출력
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("Response Body: " + responseBody);

        // Mock 동작 검증
        verify(bookService, times(1)).deleteBook(1L);
    }

    @Test
    void deleteBook_NotFound() throws Exception {
        // Mock 동작 정의
        doThrow(new RuntimeException("책을 찾을 수 없습니다.")).when(bookService).deleteBook(999L);

        // 테스트 수행
        ResultActions result =
                mockMvc.perform(delete("/api/v1/books/999")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.isSuccess").value(false))
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value("책을 찾을 수 없습니다."));

        // 응답 Body 출력
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("Response Body: " + responseBody);

        // Mock 동작 검증
        verify(bookService, times(1)).deleteBook(999L);
    }
    // 1. updateBookStatus 성공 테스트
    @Test
    void updateBookStatus_Success() throws Exception {
        // Mock 동작 정의
        doNothing().when(bookService).updateBookStatus(1L, "COMPLETED");

        // 테스트 수행
        mockMvc.perform(patch("/api/v1/books/1")
                        .param("readStatus", "COMPLETED")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 상태 코드 200 확인
                .andExpect(jsonPath("$.isSuccess").value(true)) // 성공 여부 확인
                .andExpect(jsonPath("$.message").value("okay")) // 메시지 확인
                .andExpect(jsonPath("$.result").value("도서 상태가 'COMPLETED'로 변경되었습니다.")); // result는 null
    }

    // 2. updateBookStatus 실패 테스트 (잘못된 상태값)
    @Test
    void updateBookStatus_InvalidStatus() throws Exception {
        // Mock 동작 정의
        doThrow(new IllegalArgumentException("유효하지 않은 도서 상태입니다: INVALID"))
                .when(bookService).updateBookStatus(1L, "INVALID");

        // 테스트 수행
        mockMvc.perform(patch("/api/v1/books/1")
                        .param("readStatus", "INVALID")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 상태 코드 200 확인
                .andExpect(jsonPath("$.isSuccess").value(false)) // 실패 여부 확인
                .andExpect(jsonPath("$.code").value("400")) // 코드 확인
                .andExpect(jsonPath("$.message").value("유효하지 않은 도서 상태입니다: INVALID")) // 메시지 확인
                .andExpect(jsonPath("$.result").doesNotExist()); // result는 null
    }

    // 3. updateBookStatus 실패 테스트 (도서 없음)
    @Test
    void updateBookStatus_BookNotFound() throws Exception {
        // Mock 동작 정의
        doThrow(new RuntimeException("해당 도서를 찾을 수 없습니다."))
                .when(bookService).updateBookStatus(999L, "COMPLETED");

        // 테스트 수행
        mockMvc.perform(patch("/api/v1/books/999")
                        .param("readStatus", "COMPLETED")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 상태 코드 200 확인
                .andExpect(jsonPath("$.isSuccess").value(false)) // 실패 여부 확인
                .andExpect(jsonPath("$.code").value("404")) // 코드 확인
                .andExpect(jsonPath("$.message").value("해당 도서를 찾을 수 없습니다.")) // 메시지 확인
                .andExpect(jsonPath("$.result").doesNotExist()); // result는 null
    }
}
