package com.study.oauthsociallogin.naver.controller;

import com.study.oauthsociallogin.common.response.CustomResponseEntity;
import com.study.oauthsociallogin.naver.dto.response.UserResponse;
import com.study.oauthsociallogin.naver.domain.Platform;
import com.study.oauthsociallogin.naver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class NaverController {
    private final UserService userService;

    @GetMapping("/login/naver")
    public CustomResponseEntity<UserResponse.Login> loginByNaver(@RequestParam(name = "code") String code) {
        return CustomResponseEntity.success(userService.loginByOAuth(code, Platform.NAVER));
    }
}
