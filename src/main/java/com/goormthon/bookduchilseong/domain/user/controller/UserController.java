package com.goormthon.bookduchilseong.domain.user.controller;

import com.goormthon.bookduchilseong.domain.user.dto.response.UserMyPageReseponseDto;
import com.goormthon.bookduchilseong.domain.user.service.UserService;
import com.goormthon.bookduchilseong.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService; // UserService 인터페이스 의존

    @GetMapping("/{userId}/mypage")
    public ApiResponse<?> getUserMyPage(
            @PathVariable long userId){
        try{
            UserMyPageReseponseDto response = userService.getUserMypage(userId);
            return ApiResponse.onSuccess(response);
        }catch(Exception e){
            log.error("Error retrieving mypage: {}", e.getMessage());
            return ApiResponse.onFailure("500", "내 서재/마이페이지 조회 실패", null);
        }
    }

}
