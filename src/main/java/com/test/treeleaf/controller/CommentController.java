package com.test.treeleaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.test.treeleaf.model.Comment;
import com.test.treeleaf.service.BlogService;
import com.test.treeleaf.service.CommentService;
import com.test.treeleaf.service.JwtService;
import com.test.treeleaf.service.UserInfoService;

@RestController
public class CommentController {
    private final CommentService commentService;

    private final BlogService blogService;

    private final UserInfoService userInfoService;

    private final JwtService jwtService;

    @Autowired
    public CommentController(CommentService commentService, BlogService blogService, UserInfoService userService,
            JwtService jwtService) {
        this.userInfoService = userService;
        this.blogService = blogService;
        this.commentService = commentService;
        this.jwtService = jwtService;
    }

    @PostMapping("/comment/{blogId}")
    public ResponseEntity<Comment> createComment(
            @RequestBody Comment commentRequest,
            @PathVariable Long blogId,
            @RequestHeader("Authorization") String token) {
        Long userId = jwtService.extractUserId(token.substring(7));

        commentRequest.setBlog(blogService.getBlogById(blogId));
        commentRequest.setUser(userInfoService.getUserById(userId));
        return ResponseEntity.ok(commentService.saveComment(commentRequest));
    }
}
