package com.study.oauthsociallogin.kakao.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(unique = true)
    private Long kakaoId;

    private String profilePhotoUrl;

    @Builder
    public Users(Long id, String username, Long kakaoId, String profilePhotoUrl) {
        this.id = id;
        this.username = username;
        this.kakaoId = kakaoId;
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public Users() {
    }
}
