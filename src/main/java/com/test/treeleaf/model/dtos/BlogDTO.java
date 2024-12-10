package com.test.treeleaf.model.dtos;

import java.util.List;

import com.test.treeleaf.model.Blog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlogDTO {
    private Long id;
    private String title;
    private String content;
    private UserDTO createdByUser;
    private UserDTO modifiedByUser;
    private List<CommentDTO> comments;
    private List<ThumbnailImageDTO> thumbnailImages;

    public static BlogDTO from(Blog blog) {
        if (blog == null) {
            return null;
        }
        return new BlogDTO(blog.getId(), blog.getTitle(), blog.getContent(), UserDTO.from(blog.getCreatedByUser()),
                UserDTO.from(blog.getModifiedByUser()), CommentDTO.from(blog.getComments()),
                ThumbnailImageDTO.from(blog.getThumbnailImages()));
    }

    public static List<BlogDTO> from(List<Blog> blogs) {
        if (blogs == null) {
            return List.of();
        }
        return blogs.stream().map(BlogDTO::from).toList();
    }
}
