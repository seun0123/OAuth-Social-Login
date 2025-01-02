package com.study.oauthsociallogin.common.domain;

import com.study.oauthsociallogin.naver.domain.Platform;
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
    private String email;

    @Column(nullable = false)
    private String name;

    private String profileUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Platform platform;

    @Column(unique = true)
    private Long platformId; // 플랫폼별 고유 ID (ex: Kakao ID, Naver ID 등)

    @Builder
    public Users(Long id, String email, String name, String profileUrl, Role role, Platform platform, Long platformId) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.profileUrl = profileUrl;
        this.role = role;
        this.platform = platform;
        this.platformId = platformId;
    }
}
