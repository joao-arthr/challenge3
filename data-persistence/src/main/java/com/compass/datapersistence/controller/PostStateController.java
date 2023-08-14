package com.compass.datapersistence.controller;


import com.compass.datapersistence.entity.Post;
import com.compass.datapersistence.messaging.MessageProducer;
import com.compass.datapersistence.service.PostService;
import com.compass.datapersistence.service.PostStateService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/posts")
public class PostStateController {

    private PostService postService;
    private MessageProducer messageProducer;
    private PostStateService postStateService;


    @PostMapping("/{postId}")
    public ResponseEntity<String> createPost(@PathVariable @Min(1) @Max(100) Long postId) {
            messageProducer.sendCreatedMessage(postId);
            return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> disablePost(@PathVariable @Min(1) @Max(100) Long postId) {

        var post = postService.getPostById(postId);
        if(postStateService.canBeDisabled(post)){
            messageProducer.sendDisabledMessage(postId);
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.badRequest().body("Must be an ENABLED post");
        }

    }


    @PutMapping("/{postId}")
    public ResponseEntity<String> reprocessPost(@PathVariable @Min(1) @Max(100) Long postId) {
        var post = postService.getPostById(postId);
        if(postStateService.isUpdatable(post)){
            messageProducer.sendUpdatingMessage(postId);
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.badRequest().body("Must be a DISABLED or ENABLED post");
        }

    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(){
        return ResponseEntity.ok(postService.getAllPosts());
    }



}
