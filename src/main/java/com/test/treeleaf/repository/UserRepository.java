package com.test.treeleaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.test.treeleaf.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
