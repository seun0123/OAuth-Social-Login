package com.study.oauthsociallogin.naver.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
// 네이버의 OAuth2 토큰 API 응답 데이터를 매핑
public class NaverTokenResponse {
    // 네이버에서 발급한 액세스 토큰 - API 호출 시 사용
    @JsonProperty("access_token")
    private String accessToken;
    // 리프레시 토큰 - 액세스 토큰이 만료되었을 때 갱신에 사용
    @JsonProperty("refresh_token")
    private String refreshToken;
    // 토큰의 타입 - 일반적으로 Bearer
    @JsonProperty("token_type")
    private String tokenType;
    // 액세스 토큰의 유효 기간 - 초 단위
    @JsonProperty("expires_in")
    private String expiresIn;
    // 오류 코드 - OAuth 인증 실패 시 제공
    @JsonProperty("error")
    private String error;
    // 오류에 대한 상세 설명
    @JsonProperty("error_description")
    private String errorDescription;
}
