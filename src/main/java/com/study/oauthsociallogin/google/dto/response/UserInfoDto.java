package com.study.oauthsociallogin.google.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoDto {
    private String id;

    private String email;

    private Boolean verifiedEmail;

    private String name;

    private String givenName;

    private String familyName;

    private String pictureUrl;

    private String locale;
}

