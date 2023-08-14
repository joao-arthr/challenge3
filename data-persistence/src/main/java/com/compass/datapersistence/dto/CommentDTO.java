package com.compass.datapersistence.dto;

import com.compass.datapersistence.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    Long id;
    String body;
    Post post;
}
