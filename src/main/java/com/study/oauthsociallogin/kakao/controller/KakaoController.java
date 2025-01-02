package com.study.oauthsociallogin.kakao.controller;

import com.study.oauthsociallogin.kakao.dto.response.KakaoAccessTokenDto;
import com.study.oauthsociallogin.kakao.dto.response.KakaoTokenDto;
import com.study.oauthsociallogin.kakao.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kakao")
@RequiredArgsConstructor
public class KakaoController {

    private final KakaoService kakaoService;

    @GetMapping("/callback")
    public ResponseEntity<KakaoTokenDto> kakaoCallback(@RequestParam(name = "code") String code) throws Exception {
        return ResponseEntity.ok(kakaoService.getKakaoToken(code));
    }

    @PostMapping("/login")
    public ResponseEntity<KakaoAccessTokenDto> login(@RequestBody KakaoTokenDto tokenDto) throws Exception {
        return ResponseEntity.ok(kakaoService.joinAndLogin(tokenDto));
    }
}
