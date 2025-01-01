package com.study.oauthsociallogin.google.controller;

import com.study.oauthsociallogin.google.domain.Users;
import com.study.oauthsociallogin.google.service.GoogleLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final GoogleLoginService googleLoginService;

    @GetMapping("/principalUser")
    public Users getUser(Principal principal) {
        return googleLoginService.test(principal);
    }
}
