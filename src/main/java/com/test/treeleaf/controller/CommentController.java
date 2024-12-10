package com.test.treeleaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.treeleaf.model.Comment;
import com.test.treeleaf.service.BlogService;
import com.test.treeleaf.service.CommentService;
import com.test.treeleaf.service.UserService;

@RestController
public class CommentController {
    private final CommentService commentService;

    private final BlogService blogService;

    private final UserService userService;

    @Autowired
    public CommentController(CommentService commentService, BlogService blogService, UserService userService) {
        this.userService = userService;
        this.blogService = blogService;
        this.commentService = commentService;
    }

    @PostMapping("/comment")
    public ResponseEntity<Comment> createComment(
            @RequestBody Comment commentRequest) {
        commentRequest.setBlog(blogService.getBlogById(commentRequest.getBlog().getId()));
        commentRequest.setUser(userService.getUserById(commentRequest.getUser().getId()));
        return ResponseEntity.ok(commentService.saveComment(commentRequest));
    }
}
