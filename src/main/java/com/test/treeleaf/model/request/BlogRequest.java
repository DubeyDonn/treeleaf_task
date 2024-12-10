package com.test.treeleaf.model.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogRequest {
    private String title;
    private String content;
    private Long createdByUserId;
    private Long modifiedByUserId;
    private List<MultipartFile> thumbnailImages;
}
