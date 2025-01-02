package com.study.oauthsociallogin.naver.dto.response;

import com.study.oauthsociallogin.common.domain.Users;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private String email;
    private String name;

    @Getter
    @Builder
    public static class Login {
        private String email;
        private String name;

        public static Login from(Users user) {
            return Login.builder()
                    .email(user.getEmail())
                    .name(user.getName())
                    .build();
        }
    }
}
