package com.study.oauthsociallogin.naver.service;

import com.study.oauthsociallogin.common.domain.Platform;
import com.study.oauthsociallogin.common.domain.Users;

public interface OAuth2LoginService {
    Platform supports(); // 지원하는 플랫폼을 반환
    Users toEntityUser(String code, Platform platform); // OAuth2 인증 후 사용자 데이터를 반환
}
