package com.test.treeleaf.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.test.treeleaf.model.dtos.BlogDTO;
import com.test.treeleaf.model.request.BlogRequest;
import com.test.treeleaf.service.BlogService;
import com.test.treeleaf.service.JwtService;

@RestController
public class BlogController {
    private final BlogService blogService;
    private final JwtService jwtService;

    @Autowired
    public BlogController(BlogService blogService, JwtService jwtService) {
        this.blogService = blogService;
        this.jwtService = jwtService;
    }

    @GetMapping("/blogs")
    public ResponseEntity<List<BlogDTO>> getAllBlogs() {
        return ResponseEntity.ok(blogService.getAllBlogDTOs());
    }

    @GetMapping("/blog/{id}")
    public ResponseEntity<BlogDTO> getBlogById(@PathVariable Long id) {
        return ResponseEntity.ok(blogService.getBlogDTOById(id));
    }

    @PostMapping(consumes = { "multipart/form-data" }, value = "/admin/blog")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BlogDTO> createBlog(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("thumbnailImages") List<MultipartFile> thumbnailImages,
            @RequestHeader("Authorization") String token) throws IOException {

        Long userId = jwtService.extractUserId(token.substring(7));
        BlogRequest blogRequest = new BlogRequest();
        blogRequest.setTitle(title);
        blogRequest.setContent(content);
        blogRequest.setCreatedByUserId(userId);
        blogRequest.setThumbnailImages(thumbnailImages);
        return ResponseEntity.ok(blogService.saveBlogDTO(blogRequest));
    }

    @PutMapping(consumes = { "multipart/form-data" }, value = "/admin/blog/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BlogDTO> updateBlog(
            @PathVariable Long id,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("thumbnailImages") List<MultipartFile> thumbnailImages,
            @RequestHeader("Authorization") String token) throws IOException {

        Long modifiedByUserId = jwtService.extractUserId(token.substring(7));
        BlogRequest blogRequest = new BlogRequest();
        blogRequest.setTitle(title);
        blogRequest.setContent(content);
        blogRequest.setModifiedByUserId(modifiedByUserId);
        blogRequest.setThumbnailImages(thumbnailImages);
        return ResponseEntity.ok(blogService.updateBlogDTO(id, blogRequest));
    }

    @GetMapping("/user/{userId}/blogs")
    public ResponseEntity<List<BlogDTO>> getBlogsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(blogService.getBlogDTOsByUserId(userId));
    }

    @DeleteMapping("/admin/blog/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
        return ResponseEntity.ok().build();
    }

}
