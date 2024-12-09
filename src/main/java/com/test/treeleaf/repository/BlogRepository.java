package com.test.treeleaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.treeleaf.model.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long> {

}
