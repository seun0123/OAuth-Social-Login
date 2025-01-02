package com.study.oauthsociallogin.common.repository;

import com.study.oauthsociallogin.common.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByPlatformId(String platformId);
    Optional<Users> findByEmail(String email);
}
