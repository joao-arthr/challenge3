package com.compass.datapersistence.dto;

import com.compass.datapersistence.entity.Comment;
import com.compass.datapersistence.entity.PostState;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("body")
    private String body;
    List<Comment> comments;
    List<PostState> history;
}
