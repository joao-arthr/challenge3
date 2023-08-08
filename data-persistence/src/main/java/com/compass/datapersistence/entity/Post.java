package com.compass.datapersistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Post {
    @Id
    private int id;
    private String title;
    private String body;
}
