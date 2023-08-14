package com.compass.statemanegement.controller;

import com.compass.statemanegement.dto.PostDTO;
import com.compass.statemanegement.feign.RemoteServiceClient;
import com.compass.statemanegement.messaging.MessageProducer;
import com.compass.statemanegement.service.PostStateService;
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

    private RemoteServiceClient remoteServiceClient;
    private MessageProducer messageProducer;
    private PostStateService postStateService;


    @PostMapping("/{postId}")
    public ResponseEntity<String> createPost(@PathVariable @Min(1) @Max(100) Long postId) {
            messageProducer.sendCreatedMessage(postId);
            return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> disablePost(@PathVariable @Min(1) @Max(100) Long postId) {
        var post = remoteServiceClient.getPostById(postId);
        if(postStateService.canBeDisabled(post)){
            messageProducer.sendDisabledMessage(postId);
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.badRequest().body("Must be an ENABLED post");
        }

    }


    @PutMapping("/{postId}")
    public ResponseEntity<String> reprocessPost(@PathVariable @Min(1) @Max(100) Long postId) {
        var post = remoteServiceClient.getPostById(postId);
        if(postStateService.isUpdatable(post)){
            messageProducer.sendUpdatingMessage(postId);
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.badRequest().body("Must be a DISABLED or ENABLED post");
        }

    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts(){
        return ResponseEntity.ok(remoteServiceClient.getAllPosts());
    }



}
