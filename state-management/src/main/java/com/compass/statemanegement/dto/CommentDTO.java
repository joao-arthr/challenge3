package com.compass.statemanegement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO{
    Long id;
    String body;
    PostDTO post;

}
