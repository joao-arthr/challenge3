package com.compass.datapersistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
public class PostState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime date;
    private com.compass.datapersistence.enums.PostState status;
    @ManyToOne
    @JoinColumn(name="post_id")
    @JsonBackReference
    private Post post;

}
