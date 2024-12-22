package com.study.oauthsociallogin.naver.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Users {
    private String email;
    private String name;

    @Builder
    public Users(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
