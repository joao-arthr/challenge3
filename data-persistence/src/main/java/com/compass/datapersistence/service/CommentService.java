package com.compass.datapersistence.service;

import com.compass.datapersistence.entity.Comment;
import com.compass.datapersistence.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    public List<Comment> saveOrUpdateAllComments(List<Comment> comments) {
        return commentRepository.saveAll(comments);
    }

    public void deleteAllComments(List<Comment> comments) {
        commentRepository.deleteAll(comments);
    }

}
