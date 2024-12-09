package com.test.treeleaf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.treeleaf.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBlogId(Long blogId);

    List<Comment> findByUserId(Long userId);
}
