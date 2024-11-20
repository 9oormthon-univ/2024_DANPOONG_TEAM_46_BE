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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ZodiacsignRepository zodiacsignRepository;
    private final BookClubRepository bookClubRepository;

    @Override
    public UserMyPageReseponseDto getUserMypage(Long userId) {
        // 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        // 연관 데이터 가져오기
        List<Zodiacsign> zodiacsigns = zodiacsignRepository.findByUser(user);
        List<Book> books = bookRepository.findBooksByUserId(userId);
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

    private int calculateProgress(int startPoint, int totalPage) {
        return (int) ((startPoint / (double) totalPage) * 100);
    }
}
