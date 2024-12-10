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

    private final UserInfoService userInfoService;

    private final FileStorageService fileStorageService;

    private final ThumbnailImageRepository thumbnailImageRepository;

    @Autowired
    public BlogService(BlogRepository blogRepository, UserInfoService userService,
            FileStorageService fileStorageService,
            ThumbnailImageRepository thumbnailImageRepository) {
        this.blogRepository = blogRepository;
        this.userInfoService = userService;
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

    public List<BlogDTO> getBlogDTOsByUserId(Long userId) {
        List<Blog> blogs = blogRepository.findByCreatedByUserId(userId);
        return BlogDTO.from(blogs);
    }

    public List<Blog> getBlogsByUserId(Long userId) {
        return blogRepository.findByCreatedByUserId(userId);
    }

    public Blog saveBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    public void deleteBlog(Long id) {
        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog == null) {
            return;
        }
        fileStorageService.deleteFiles(blog.getThumbnailImages());
        blogRepository.deleteById(id);
    }

    public BlogDTO saveBlogDTO(BlogRequest blogRequest) {
        Blog blog = new Blog();
        blog.setTitle(blogRequest.getTitle());
        blog.setContent(blogRequest.getContent());
        blog.setCreatedByUser(userInfoService.getUserById(blogRequest.getCreatedByUserId()));

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
        blog.setModifiedByUser(userInfoService.getUserById(blogRequest.getModifiedByUserId()));

        // Delete previous thumbnails
        thumbnailImageRepository.deleteByBlogId(blog.getId());

        fileStorageService.deleteFiles(blog.getThumbnailImages());

        blog.getThumbnailImages().clear();
        for (MultipartFile file : blogRequest.getThumbnailImages()) {
            String fileName = fileStorageService.storeFile(file);
            ThumbnailImage thumbnailImage = new ThumbnailImage();
            thumbnailImage.setName(fileName);
            thumbnailImage.setBlog(blog);
            blog.getThumbnailImages().add(thumbnailImage);
        }
        return BlogDTO.from(blogRepository.save(blog));
    }
}
