package com.compass.datapersistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Comment {
    @Id
    private Long id;
    private String body;
    @ManyToOne
    @JoinColumn(name="post_id")
    @JsonBackReference
    private Post post;
}
