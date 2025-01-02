package com.study.oauthsociallogin.google.service;

import com.google.gson.Gson;
import com.study.oauthsociallogin.common.repository.UsersRepository;
import com.study.oauthsociallogin.config.TokenProvider;
import com.study.oauthsociallogin.common.domain.Users;
import com.study.oauthsociallogin.google.dto.response.TokenDto;
import com.study.oauthsociallogin.google.dto.response.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.net.URI;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GoogleService {

    @Value("${google.client-id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;

    @Value("${google.token-uri}")
    private String GOOGLE_TOKEN_URL;

    @Value("${google.redirect-uri}")
    private String GOOGLE_REDIRECT_URI;

    private final UsersRepository usersRepository;
    private final TokenProvider tokenProvider;

    public String getGoogleAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = Map.of(
                "code", code,
                "scope", "https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email",
                "client_id", GOOGLE_CLIENT_ID,
                "client_secret", GOOGLE_CLIENT_SECRET,
                "redirect_uri", GOOGLE_REDIRECT_URI,
                "grant_type", "authorization_code"
        );

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(GOOGLE_TOKEN_URL, params, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String json = responseEntity.getBody();
            Gson gson = new Gson();
            return gson.fromJson(json, TokenDto.class).getAccessToken();
        }

        throw new RuntimeException("구글 엑세스 토큰을 가져오는데 실패했습니다.");
    }

    public TokenDto loginOrSignUp(String googleAccessToken) {
        UserInfoDto userInfoDto = getUserInfo(googleAccessToken);

        Users user = usersRepository.findByEmail(userInfoDto.getEmail())
                .orElseGet(() -> usersRepository.save(Users.builder()
                        .platformId(userInfoDto.getEmail())
                        .email(userInfoDto.getEmail())
                        .name(userInfoDto.getName())
                        .profileUrl(userInfoDto.getPictureUrl())
                        .role(Users.Role.USER)
                        .platform(Users.Platform.GOOGLE)
                        .build())
                );

        return TokenDto.builder()
                .accessToken(tokenProvider.createAccessToken(user))
                .build();
    }

    private UserInfoDto getUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + accessToken;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(url));
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String json = responseEntity.getBody();
            Gson gson = new Gson();
            return gson.fromJson(json, UserInfoDto.class);
        }

        throw new RuntimeException("유저 정보를 가져오는데 실패했습니다.");
    }
}
