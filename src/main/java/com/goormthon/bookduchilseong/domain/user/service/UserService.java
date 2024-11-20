    package com.goormthon.bookduchilseong.domain.user.service;

    import com.goormthon.bookduchilseong.domain.user.dto.response.UserMyPageReseponseDto;
    import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

    public interface UserService {
        // 마이페이지 조회
        UserMyPageReseponseDto getUserMypage(Long userId);
    }
