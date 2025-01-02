package com.study.oauthsociallogin.kakao.repository;

import com.study.oauthsociallogin.kakao.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByKakaoId(Long kakaoId);
}
