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
import com.goormthon.bookduchilseong.global.auth.dto.response.KakaoUserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.aspectj.bridge.MessageUtil.print;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ZodiacsignRepository zodiacsignRepository;
    private final BookClubRepository bookClubRepository;

    @Override
    public User findOrCreateUser(KakaoUserResponseDto kakaoUser) {

        // 1. 닉네임 확인
        if(kakaoUser.properties().nickname() == null || kakaoUser.properties().nickname().isEmpty()){
            throw new IllegalArgumentException("카카오 닉네임을 가져올 수 없습니다. : " + kakaoUser.properties().nickname());
        }

//        User newUser0 = new User(
//                kakaoUser.id(), // ID는 자동 생성
//                kakaoUser.properties().nickname(),
//                "0", // 새로운 유저의 경우 뽑기 횟수 => 0
//                kakaoUser.properties().thumbnailImage(), // 카카오 프로필 이미지
//                null, // 프로필 별자리 이름 (별자리)
//                null // 프로필 별자리 uri (별자리)
//        );
//
//        return newUser0;
        // 2. 기존 사용자 조회
        return userRepository.findByName(kakaoUser.properties().nickname()).orElseGet(() -> {
                    // 3. 새 사용자 저장
                    User newUser = new User(
                            kakaoUser.id(), // ID는 자동 생성
                            kakaoUser.properties().nickname(),
                            0, // 새로운 유저의 경우 뽑기 횟수 => 0
                            kakaoUser.properties().thumbnailImage(), // 카카오 프로필 이미지
                            null, // 프로필 별자리 이름 (별자리)
                            null // 프로필 별자리 uri (별자리)
                    );
                    return userRepository.save(newUser);
                });
    }

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
