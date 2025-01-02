package com.study.oauthsociallogin.kakao.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.oauthsociallogin.common.domain.Users;
import com.study.oauthsociallogin.config.TokenProvider;
import com.study.oauthsociallogin.kakao.dto.response.KakaoAccessTokenDto;
import com.study.oauthsociallogin.kakao.dto.response.KakaoTokenDto;
import com.study.oauthsociallogin.common.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoService {

    private final UsersRepository usersRepository;
    private final TokenProvider tokenProvider;

    @Value("${kakao.client_id}")
    private String kakaoClientId;

    @Value("${kakao.redirect_uri}")
    private String redirectUri;

    public KakaoTokenDto getKakaoToken(String code) {
        String reqURL = "https://kauth.kakao.com/oauth/token";

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("grant_type", "authorization_code");
        requestParams.add("client_id", kakaoClientId);
        requestParams.add("redirect_uri", redirectUri);
        requestParams.add("code", code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestParams, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(reqURL, HttpMethod.POST, requestEntity, String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());
            return KakaoTokenDto.builder()
                    .accessToken(jsonNode.get("access_token").asText())
                    .refreshToken(jsonNode.get("refresh_token").asText())
                    .tokenType(jsonNode.get("token_type").asText())
                    .expiresIn(jsonNode.get("expires_in").asInt())
                    .scope(jsonNode.get("scope").asText())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("카카오 토큰 처리 중 오류 발생", e);
        }
    }

    public KakaoAccessTokenDto joinAndLogin(KakaoTokenDto tokenDto) {
        String reqURL = "https://kapi.kakao.com/v2/user/me";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + tokenDto.getAccessToken());
            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.exchange(reqURL, HttpMethod.GET, requestEntity, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());

            Long kakaoId = jsonNode.get("id").asLong();
            String email = jsonNode.path("kakao_account").path("email").asText();
            String name = jsonNode.path("properties").path("nickname").asText();
            String profileUrl = jsonNode.path("properties").path("profile_image").asText();

            Users user = usersRepository.findByPlatformId(kakaoId.toString())
                    .orElseGet(() -> usersRepository.save(Users.builder()
                            .platformId(kakaoId.toString())
                            .email(email)
                            .name(name)
                            .profileUrl(profileUrl)
                            .role(Users.Role.USER)
                            .platform(Users.Platform.KAKAO)
                            .build())
                    );

            String accessToken = tokenProvider.createAccessToken(user);

            return KakaoAccessTokenDto.builder()
                    .accessToken(accessToken)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("카카오 사용자 정보 처리 중 오류 발생", e);
        }
    }
}
