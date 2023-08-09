package com.compass.datapersistence.dto;

import com.compass.datapersistence.entity.Post;
import java.time.LocalDateTime;

public record HistoryDTO(Integer id, String status, LocalDateTime timestamp, Post post) {
}
