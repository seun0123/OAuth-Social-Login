package com.study.oauthsociallogin.google.controller;

import com.study.oauthsociallogin.google.dto.response.TokenDto;
import com.study.oauthsociallogin.google.service.GoogleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/api")
public class AuthController {

    private final GoogleService googleService;

    @GetMapping("/login/google")
    public TokenDto googleCallback(@RequestParam(name = "code") String code) {
        String googleAccessToken = googleService.getGoogleAccessToken(code);
        return loginOrSignup(googleAccessToken);
    }

    private TokenDto loginOrSignup(String googleAccessToken) {
        return googleService.loginOrSignUp(googleAccessToken);
    }
}
