package com.study.oauthsociallogin.common.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String platformId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String profileUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Platform platform;

    @Builder
    public Users(Long id, String platformId, String email, String name, String profileUrl, Role role, Platform platform) {
        this.id = id;
        this.platformId = platformId;
        this.email = email;
        this.name = name;
        this.profileUrl = profileUrl;
        this.role = role;
        this.platform = platform;
    }

    public enum Role {
        USER, ADMIN
    }

    public enum Platform {
        GOOGLE, KAKAO, NAVER, GITHUB
    }
}
