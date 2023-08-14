package com.compass.datapersistence.controller;
import com.compass.datapersistence.dto.PostDTO;
import com.compass.datapersistence.entity.Post;
import com.compass.datapersistence.exception.PostNotFoundException;
import com.compass.datapersistence.service.PostService;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
        Post postEntity = modelMapper.map(postDTO, Post.class);
        Post createdPost = postService.createPost(postEntity);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{Id}")
                .buildAndExpand(createdPost.getId())
                .toUri();

        return ResponseEntity.created(location).body(modelMapper.map(createdPost,PostDTO.class));
    }

    @GetMapping
    public List<PostDTO> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return posts.stream()
                .map(post -> modelMapper.map(post, PostDTO.class))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable @Range(min=0,max=101) Long id) {
        Post postEntity = postService.getPostById(id);
         try{
            PostDTO postDTO = modelMapper.map(postEntity, PostDTO.class);
            return ResponseEntity.ok(postDTO);
         } catch(PostNotFoundException ex){
            return ResponseEntity.notFound().build();
         }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO) {
        try {
            Post existingPost = postService.getPostById(id);
            Post updatedPost = modelMapper.map(postDTO, Post.class);

            existingPost.setTitle(updatedPost.getTitle());
            existingPost.setBody(updatedPost.getBody());

            postService.createPost(existingPost);

            return ResponseEntity.ok(modelMapper.map(existingPost, PostDTO.class));
        } catch (PostNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{postId}")
    public boolean doesPostExists(@PathVariable Long postId){
        return postService.doesPostExists(postId);
    }

}