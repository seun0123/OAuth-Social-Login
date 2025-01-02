package com.study.oauthsociallogin.naver.service;

import com.study.oauthsociallogin.common.domain.Platform;
import com.study.oauthsociallogin.common.domain.Users;
import com.study.oauthsociallogin.naver.dto.response.UserResponse;
import com.study.oauthsociallogin.common.exception.CustomException;
import com.study.oauthsociallogin.common.constants.ErrorMessages;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final List<OAuth2LoginService> oAuth2LoginServices;

    public UserResponse.Login loginByOAuth(String code, Platform platform) {
        Users userEntity = null;

        // 지원하는 플랫폼을 찾아 해당 서비스 호출
        for (OAuth2LoginService oAuth2LoginService : oAuth2LoginServices) {
            if (oAuth2LoginService.supports().equals(platform)) {
                userEntity = oAuth2LoginService.toEntityUser(code, platform);
                break;
            }
        }

        // 처리된 유저가 없는 경우 예외 처리
        if (userEntity == null) {
            throw new CustomException(ErrorMessages.UNEXPECTED_EXCEPTION);
        }

        // 이후 로직 구현
        return UserResponse.Login.from(userEntity);
    }
}
