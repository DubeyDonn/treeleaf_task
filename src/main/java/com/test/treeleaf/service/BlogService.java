package com.test.treeleaf.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.test.treeleaf.model.Blog;
import com.test.treeleaf.model.ThumbnailImage;
import com.test.treeleaf.model.dtos.BlogDTO;
import com.test.treeleaf.model.request.BlogRequest;
import com.test.treeleaf.repository.BlogRepository;
import com.test.treeleaf.repository.ThumbnailImageRepository;

@Service
public class BlogService {
    private final BlogRepository blogRepository;

    private final UserService userService;

    private final FileStorageService fileStorageService;

    private final ThumbnailImageRepository thumbnailImageRepository;

    @Autowired
    public BlogService(BlogRepository blogRepository, UserService userService, FileStorageService fileStorageService,
            ThumbnailImageRepository thumbnailImageRepository) {
        this.blogRepository = blogRepository;
        this.userService = userService;
        this.fileStorageService = fileStorageService;
        this.thumbnailImageRepository = thumbnailImageRepository;
    }

    public List<BlogDTO> getAllBlogDTOs() {
        List<Blog> blogs = blogRepository.findAll();
        return BlogDTO.from(blogs);
    }

    public BlogDTO getBlogDTOById(Long id) {
        Blog blog = blogRepository.findById(id).orElse(null);
        return BlogDTO.from(blog);
    }

    public Blog getBlogById(Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    public List<Blog> getBlogsByUserId(Long userId) {
        return blogRepository.findByCreatedByUserId(userId);
    }

    public Blog saveBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    public BlogDTO saveBlogDTO(BlogRequest blogRequest) {
        Blog blog = new Blog();
        blog.setTitle(blogRequest.getTitle());
        blog.setContent(blogRequest.getContent());
        blog.setCreatedByUser(userService.getUserById(blogRequest.getCreatedByUserId()));

        List<ThumbnailImage> thumbnailImageList = new ArrayList<>();
        for (MultipartFile file : blogRequest.getThumbnailImages()) {
            String fileName = fileStorageService.storeFile(file);
            ThumbnailImage thumbnailImage = new ThumbnailImage();
            thumbnailImage.setName(fileName);
            thumbnailImage.setBlog(blog);
            thumbnailImageList.add(thumbnailImage);
        }
        blog.setThumbnailImages(thumbnailImageList);
        return BlogDTO.from(blogRepository.save(blog));
    }

    public BlogDTO updateBlogDTO(Long id, BlogRequest blogRequest) {
        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog == null) {
            return null;
        }
        blog.setTitle(blogRequest.getTitle());
        blog.setContent(blogRequest.getContent());
        blog.setModifiedByUser(userService.getUserById(blogRequest.getModifiedByUserId()));

        // Delete previous thumbnails
        thumbnailImageRepository.deleteByBlogId(blog.getId());

        List<ThumbnailImage> thumbnailImageList = new ArrayList<>();
        for (MultipartFile file : blogRequest.getThumbnailImages()) {
            String fileName = fileStorageService.storeFile(file);
            ThumbnailImage thumbnailImage = new ThumbnailImage();
            thumbnailImage.setName(fileName);
            thumbnailImage.setBlog(blog);
            thumbnailImageList.add(thumbnailImage);
        }
        blog.setThumbnailImages(thumbnailImageList);
        return BlogDTO.from(blogRepository.save(blog));
    }
}
