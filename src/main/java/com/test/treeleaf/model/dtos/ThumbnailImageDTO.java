package com.test.treeleaf.model.dtos;

import java.util.List;

import com.test.treeleaf.model.ThumbnailImage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ThumbnailImageDTO {
    private Long id;
    private String name;
    private Long blogId;

    public static ThumbnailImageDTO from(ThumbnailImage thumbnailImage) {
        if (thumbnailImage == null) {
            return null;
        }
        return new ThumbnailImageDTO(thumbnailImage.getId(), thumbnailImage.getName(),
                thumbnailImage.getBlog().getId());
    }

    public static List<ThumbnailImageDTO> from(List<ThumbnailImage> thumbnailImages) {
        if (thumbnailImages == null) {
            return List.of();
        }
        return thumbnailImages.stream().map(ThumbnailImageDTO::from).toList();
    }
}
