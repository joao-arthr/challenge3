package com.compass.datapersistence.dto;

import com.compass.datapersistence.entity.Comment;
import com.compass.datapersistence.entity.PostState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    Integer id;
    String title;
    String body;
    List<Comment> comments;
    List<PostState> history;
}
