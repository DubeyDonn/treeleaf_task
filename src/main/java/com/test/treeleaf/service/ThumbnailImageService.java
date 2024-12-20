package com.test.treeleaf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.treeleaf.repository.ThumbnailImageRepository;

@Service
public class ThumbnailImageService {
    private final ThumbnailImageRepository thumbnailImageRepository;

    @Autowired
    public ThumbnailImageService(ThumbnailImageRepository thumbnailImageRepository) {
        this.thumbnailImageRepository = thumbnailImageRepository;
    }

    public void deleteThumbnailImage(Long id) {
        thumbnailImageRepository.deleteById(id);
    }

    public void deleteThumbnailImageByBlogId(Long blogId) {
        thumbnailImageRepository.deleteByBlogId(blogId);
    }
}
