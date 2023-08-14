package com.compass.statemanegement.feign;

import com.compass.statemanegement.dto.CommentDTO;
import com.compass.statemanegement.dto.PostDTO;
import com.compass.statemanegement.dto.PostStateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="data-persistence")
public interface RemoteServiceClient {

    @PostMapping("/api/posts")
    void createPost(PostDTO postDTO);

    @GetMapping("/api/posts/{postId}")
    PostDTO getPostById(@PathVariable("postId") Long postId);

    @GetMapping("/api/posts")
    List<PostDTO> getAllPosts();

    @PutMapping("/api/posts/{postId}")
    void updatePost(@PathVariable("postId") Long postId, PostDTO postDTO);

    @PostMapping("/api/comments")
    List<CommentDTO> createComments(@RequestBody List<CommentDTO> commentDTOList);

    @GetMapping("/api/comments/{postId}")
    ResponseEntity<List<CommentDTO>> getAllComments(@PathVariable Long postId);

    @PostMapping("/api/status")
    public ResponseEntity<PostStateDTO> createHistory(@RequestBody PostStateDTO postStateDTO);

    @GetMapping("/api/status/{postId}")
    public ResponseEntity<List<PostStateDTO>> getAllHistories(@PathVariable Long postId);

    @GetMapping("/api/posts/exists/{postId}")
    public boolean doesPostExists(@PathVariable Long postId);

}
