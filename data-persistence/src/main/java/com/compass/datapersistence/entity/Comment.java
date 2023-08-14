package com.compass.datapersistence.entity;

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
    private Post post;
}
