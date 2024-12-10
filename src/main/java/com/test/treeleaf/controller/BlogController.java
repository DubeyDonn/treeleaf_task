package com.test.treeleaf.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.test.treeleaf.model.dtos.BlogDTO;
import com.test.treeleaf.model.request.BlogRequest;
import com.test.treeleaf.service.BlogService;

@RestController
public class BlogController {
    private final BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/blogs")
    public ResponseEntity<List<BlogDTO>> getAllBlogs() {
        return ResponseEntity.ok(blogService.getAllBlogDTOs());
    }

    @GetMapping("/blog/{id}")
    public ResponseEntity<BlogDTO> getBlogById(@PathVariable Long id) {
        return ResponseEntity.ok(blogService.getBlogDTOById(id));
    }

    @PostMapping(consumes = { "multipart/form-data" }, value = "/blog")
    public ResponseEntity<BlogDTO> saveBlog(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("createdByUserId") Long createdByUserId,
            @RequestParam("thumbnailImages") List<MultipartFile> thumbnailImages) throws IOException {

        BlogRequest blogRequest = new BlogRequest();
        blogRequest.setTitle(title);
        blogRequest.setContent(content);
        blogRequest.setCreatedByUserId(createdByUserId);
        blogRequest.setThumbnailImages(thumbnailImages);
        return ResponseEntity.ok(blogService.saveBlogDTO(blogRequest));
    }

    @PutMapping(consumes = { "multipart/form-data" }, value = "/blog/{id}")
    public ResponseEntity<BlogDTO> updateBlog(
            @PathVariable Long id,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("modifiedByUserId") Long modifiedByUserId,
            @RequestParam("thumbnailImages") List<MultipartFile> thumbnailImages) throws IOException {

        BlogRequest blogRequest = new BlogRequest();
        blogRequest.setTitle(title);
        blogRequest.setContent(content);
        blogRequest.setModifiedByUserId(modifiedByUserId);
        blogRequest.setThumbnailImages(thumbnailImages);
        return ResponseEntity.ok(blogService.updateBlogDTO(id, blogRequest));
    }
}
