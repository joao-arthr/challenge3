package com.compass.statemanegement.dto;

import com.compass.statemanegement.enums.PostState;

import java.time.LocalDateTime;

public record PostStateDTO(Long id, Long postId, LocalDateTime date, PostState state) {
}
