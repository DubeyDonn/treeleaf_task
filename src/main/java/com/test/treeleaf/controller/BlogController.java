package com.test.treeleaf.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.test.treeleaf.model.Blog;
import com.test.treeleaf.model.ThumbnailImage;
import com.test.treeleaf.service.BlogService;
import com.test.treeleaf.service.FileStorageService;
import com.test.treeleaf.service.UserService;

@RestController
public class BlogController {
    private final BlogService blogService;

    private final UserService userService;

    private final FileStorageService fileStorageService;

    @Autowired
    public BlogController(BlogService blogService, UserService userService,
            FileStorageService fileStorageService) {
        this.blogService = blogService;
        this.userService = userService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/blogs")
    public List<Blog> getAllBlogs() {
        return blogService.getAllBlogs();
    }

    @PostMapping(consumes = { "multipart/form-data" }, value = "/blog")
    public ResponseEntity<Blog> saveBlog(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("createdByUserId") Long createdByUserId,
            @RequestParam("thumbnailImages") List<MultipartFile> thumbnailImages) throws IOException {

        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setContent(content);
        blog.setCreatedByUser(userService.getUserById(createdByUserId));

        List<ThumbnailImage> thumbnailImageList = new ArrayList<>();
        for (MultipartFile file : thumbnailImages) {
            String fileName = fileStorageService.storeFile(file);
            ThumbnailImage thumbnailImage = new ThumbnailImage();
            thumbnailImage.setName(fileName);
            thumbnailImage.setBlog(blog);
            thumbnailImageList.add(thumbnailImage);
        }
        blog.setThumbnailImages(thumbnailImageList);

        Blog savedBlog = blogService.saveBlog(blog);
        return ResponseEntity.ok(savedBlog);
    }
}
