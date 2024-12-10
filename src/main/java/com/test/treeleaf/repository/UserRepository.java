package com.test.treeleaf.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.test.treeleaf.model.UserInfo;

public interface UserRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByUsername(String username);

    UserInfo findAllByUsername(String username);
}
