package com.study.oauthsociallogin.kakao.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class KakaoAccessTokenDto {
    private String accessToken;
}
