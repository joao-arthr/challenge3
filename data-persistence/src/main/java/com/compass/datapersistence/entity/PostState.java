package com.compass.datapersistence.entity;

import com.compass.datapersistence.enums.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PostState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private Status status;
    @ManyToOne
    @JoinColumn(name="post_id")
    @JsonBackReference
    private Post post;

    public PostState(Post post, LocalDateTime date, Status status){
        this.post = post;
        this.date = date;
        this.status = status;
    }

}
