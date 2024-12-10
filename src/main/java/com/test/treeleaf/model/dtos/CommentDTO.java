package com.test.treeleaf.model.dtos;

import java.util.List;

import com.test.treeleaf.model.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long id;
    private String content;
    private UserDTO user;
    private Long blogId;

    public static CommentDTO from(Comment comment) {
        if (comment == null) {
            return null;
        }
        return new CommentDTO(comment.getId(), comment.getContent(), UserDTO.from(comment.getUser()),
                comment.getBlog().getId());
    }

    public static List<CommentDTO> from(List<Comment> comments) {
        if (comments == null) {
            return List.of();
        }
        return comments.stream().map(CommentDTO::from).toList();
    }
}
