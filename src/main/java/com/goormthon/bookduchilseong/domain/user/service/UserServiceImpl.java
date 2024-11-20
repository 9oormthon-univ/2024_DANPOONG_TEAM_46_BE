package com.goormthon.bookduchilseong.domain.user.service;

import com.goormthon.bookduchilseong.domain.book.entity.Book;
import com.goormthon.bookduchilseong.domain.book.repository.BookRepository;
import com.goormthon.bookduchilseong.domain.bookclub.entity.BookClub;
import com.goormthon.bookduchilseong.domain.bookclub.repository.BookClubRepository;
import com.goormthon.bookduchilseong.domain.user.dto.response.UserMyPageReseponseDto;
import com.goormthon.bookduchilseong.domain.user.entity.User;
import com.goormthon.bookduchilseong.domain.user.repository.UserRepository;
import com.goormthon.bookduchilseong.domain.zodiacsign.entity.Zodiacsign;
import com.goormthon.bookduchilseong.domain.zodiacsign.repository.ZodiacsignRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ZodiacsignRepository zodiacsignRepository;
    private final BookClubRepository bookClubRepository;
    UserMyPageReseponseDto getUserMypage(Long userId){
        // 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        // 유저 ID로 연관된 별자리 데이터 가져오기
        List<Zodiacsign> zodiacsigns = zodiacsignRepository.findByUser(user);
        // 서재 정보 가져오기
        List<Book> books = bookRepository.findBooksByUserId(userId);
        // 특정 사용자가 참여 중인 북클럽 목록 가져오기
        List<BookClub> bookClubs = bookClubRepository.findByUser(user);
        // 책 리스트를 myBook 형식으로 매핑
        List<UserMyPageReseponseDto.MyBook> myBooks = books.stream()
                .map(book -> new UserMyPageReseponseDto.MyBook(
                        book.getTitle(),
                        book.getAuthor(),
                        book.getTotalPage(),
                        book.getReadPage(),
                        calculateProgress(book.getReadPage(), book.getTotalPage())
                ))
                .toList();

        // 마이페이지 응답 생성
        return new UserMyPageReseponseDto(
                zodiacsigns.size(),
                books.size(),
                bookClubs.size(),
                myBooks
        );

    }
    // 책의 진행도를 계산하는 메서드
    private int calculateProgress(int startPoint, int totalPage){
        return (int) ((startPoint/totalPage) * 100);
    }

}
