package com.compass.statemanegement.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO{
        @DecimalMin(value = "1", message = "Age must be at least 1")
        @DecimalMax(value = "100", message = "Age must be at most 100")
        Long id;
        String title;
        String body;
        List<CommentDTO> comments;
        List<PostStateDTO> history;

        public static PostDTO createdConstructor(Long id) {
                return new PostDTO(id, null, null, new ArrayList<>(), new ArrayList<>());
        }

        public void addStateToHistory(PostStateDTO newState) {
                this.history.add(newState);
        }
}
