package com.compass.datapersistence.service;

import com.compass.datapersistence.entity.Post;
import com.compass.datapersistence.exception.PostNotFoundException;
import com.compass.datapersistence.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("Post not found"));
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
