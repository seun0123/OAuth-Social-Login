package com.study.oauthsociallogin.naver.service;

import com.study.oauthsociallogin.naver.config.NaverProperties;
import com.study.oauthsociallogin.naver.domain.Platform;
import com.study.oauthsociallogin.naver.domain.Users;
import com.study.oauthsociallogin.naver.dto.response.NaverTokenResponse;
import com.study.oauthsociallogin.naver.dto.response.NaverUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class NaverLoginService implements OAuth2LoginService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final NaverProperties naverProperties;

    @Override
    public Platform supports() {
        return Platform.NAVER; // 네이버 플랫폼을 지원
    }

    @Override
    public Users toEntityUser(String code, Platform platform) {
        String accessToken = toRequestAccessToken(code); // 액세스 토큰 요청
        NaverUserResponse.NaverUserDetail profile = toRequestProfile(accessToken); // 사용자 정보 요청

        // Users 엔티티 생성 및 반환
        return Users.builder()
                .email(profile.getEmail())
                .name(profile.getName())
                .build();
    }

    // 액세스 토큰 요청 메서드
    private String toRequestAccessToken(String code) {
        ResponseEntity<NaverTokenResponse> response =
                restTemplate.exchange(
                        naverProperties.getRequestURL(code), // 네이버 API URL 생성
                        HttpMethod.GET,
                        null,
                        NaverTokenResponse.class
                );

        // 액세스 토큰 반환
        return response.getBody().getAccessToken();
    }

    // 사용자 프로필 요청 메서드
    private NaverUserResponse.NaverUserDetail toRequestProfile(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken); // Bearer 토큰 설정
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        ResponseEntity<NaverUserResponse> response =
                restTemplate.exchange(
                        "https://openapi.naver.com/v1/nid/me", // 네이버 사용자 정보 API
                        HttpMethod.GET,
                        request,
                        NaverUserResponse.class
                );

        // 사용자 정보 반환
        return response.getBody().getNaverUserDetail();
    }
}
