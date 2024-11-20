package com.goormthon.bookduchilseong.domain.user.service;

import com.goormthon.bookduchilseong.domain.book.entity.Book;
import com.goormthon.bookduchilseong.domain.book.repository.BookRepository;
import com.goormthon.bookduchilseong.domain.user.dto.response.UserMyPageReseponseDto;
import com.goormthon.bookduchilseong.domain.user.entity.User;
import com.goormthon.bookduchilseong.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    UserMyPageReseponseDto getUserMypage(Long userId){
        // 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        // 서재 정보 가져오기
        List<Book> books = bookRepository.findBooksByUserId(userId);

        // 마이페이지 응답 생성

    }
}
