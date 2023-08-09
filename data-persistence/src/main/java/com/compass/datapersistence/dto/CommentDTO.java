package com.compass.datapersistence.dto;

import com.compass.datapersistence.entity.Post;

public record CommentDTO(Long id, String body, Post post) {
}
