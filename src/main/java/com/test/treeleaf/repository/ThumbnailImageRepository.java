package com.test.treeleaf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.treeleaf.model.ThumbnailImage;

public interface ThumbnailImageRepository extends JpaRepository<ThumbnailImage, Long> {
    List<ThumbnailImage> findByBlogId(Long blogId);

    void deleteByBlogId(Long blogId);
}
