package com.compass.datapersistence.dto;

import com.compass.datapersistence.entity.Post;
import com.compass.datapersistence.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostStateDTO{
    Integer id;
    Status status;
    LocalDateTime date;
    Post post;
}
