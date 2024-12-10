package com.test.treeleaf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.treeleaf.model.ThumbnailImage;

import jakarta.transaction.Transactional;

public interface ThumbnailImageRepository extends JpaRepository<ThumbnailImage, Long> {
    List<ThumbnailImage> findByBlogId(Long blogId);

    @Transactional
    void deleteByBlogId(Long blogId);
}
