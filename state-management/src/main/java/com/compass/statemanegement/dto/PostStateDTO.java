package com.compass.statemanegement.dto;

import com.compass.statemanegement.enums.PostState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostStateDTO{
        Long id;
        PostDTO post;
        LocalDateTime date;
        PostState status;

        public PostStateDTO(PostDTO post, LocalDateTime date, PostState status){
            this.post = post;
            this.date = date;
            this.status = status;
        }
}
