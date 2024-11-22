    package com.goormthon.bookduchilseong.domain.user.service;

    import com.goormthon.bookduchilseong.domain.user.dto.response.UserMyPageReseponseDto;
    import com.goormthon.bookduchilseong.domain.user.entity.User;
    import com.goormthon.bookduchilseong.global.auth.dto.response.KakaoUserResponseDto;

    public interface UserService {
        User findOrCreateUser(KakaoUserResponseDto kakaoUser);
        // 마이페이지 조회
        UserMyPageReseponseDto getUserMypage(Long userId);
    }
