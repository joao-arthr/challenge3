package com.compass.datapersistence.dto;

import com.compass.datapersistence.entity.Post;
import com.compass.datapersistence.enums.PostState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostStateDTO{
    Integer id;
    PostState status;
    LocalDateTime date;
    Post post;
}
