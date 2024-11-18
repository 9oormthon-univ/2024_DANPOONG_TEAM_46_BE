package com.goormthon.bookduchilseong.domain.book.dto.request.book.service;

import com.goormthon.bookduchilseong.domain.book.dto.request.book.dto.request.BookRequestDto;
import com.goormthon.bookduchilseong.domain.book.dto.request.book.dto.request.BookResponseDto;
import com.goormthon.bookduchilseong.domain.book.dto.request.book.entity.Book;
import com.goormthon.bookduchilseong.domain.book.dto.request.book.entity.ReadStatus;
import com.goormthon.bookduchilseong.domain.book.dto.request.book.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public BookResponseDto addBook(BookRequestDto requestDto) {
        Book book = Book.builder()
                .userId(requestDto.getUserId())
                .title(requestDto.getTitle())
                .author(requestDto.getAuthor())
                .totalPage(requestDto.getTotalPage())
                .goalDayPage(requestDto.getGoalDayPage())
                .readPage(0L)
                .status(requestDto.getStatus()) // Enum 값 저장
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .isDeleted(false)
                .build();
        bookRepository.save(book);
        return new BookResponseDto(book);
    }

    @Override
    public List<BookResponseDto> getAllBooks(Long userId) {
        return bookRepository.findByUserId(userId).stream()
                .map(BookResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponseDto getBookDetail(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("해당 도서를 찾을 수 없습니다."));
        return new BookResponseDto(book);
    }

    @Override
    public void deleteBook(Long bookId) {
        // Book을 ID로 조회, 없으면 예외 발생
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("해당 도서를 찾을 수 없습니다."));

        // isDeleted를 true로 설정하여 소프트 삭제 처리
        book.setIsDeleted(true);

        // 변경 사항 저장
        bookRepository.save(book);
    }
    @Override
    @Transactional
    public void updateBookStatus(Long bookId, String readStatus) {
        // Book 조회
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("해당 도서를 찾을 수 없습니다."));

        // Enum으로 상태 업데이트
        try {
            ReadStatus status = ReadStatus.valueOf(readStatus.toUpperCase());
            book.setStatus(status);
            book.setUpdateAt(LocalDateTime.now());
            bookRepository.save(book);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("유효하지 않은 도서 상태입니다: " + readStatus);
        }
    }

}
