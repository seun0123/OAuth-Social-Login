package com.study.oauthsociallogin.kakao.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class KakaoTokenDto {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private int expiresIn;
    private String scope;
}
