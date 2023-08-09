package com.compass.datapersistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Comment {
    @Id
    private int id;
    private String body;
    @ManyToOne
    private Post post;
}
