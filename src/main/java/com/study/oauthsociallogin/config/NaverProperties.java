package com.study.oauthsociallogin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponentsBuilder;

// OAuth 관련 네이버 설정 정보를 관리하고 요청 URL 생성과 같은 유틸리티 메서드를 제공
@Data
@Configuration
// application.yml에 있는 naver로 시작하는 설정 값을 매핑
@ConfigurationProperties(prefix = "naver")
public class NaverProperties {
    private String requestTokenUri;
    private String clientId;
    private String clientSecret;

    public String getRequestURL(String code) {
        return UriComponentsBuilder.fromHttpUrl(requestTokenUri)
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("code", code)
                .toUriString();
    }
}
