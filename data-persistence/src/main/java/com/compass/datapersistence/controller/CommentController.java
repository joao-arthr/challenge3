package com.compass.datapersistence.controller;

import com.compass.datapersistence.dto.CommentDTO;
import com.compass.datapersistence.entity.Comment;
import com.compass.datapersistence.service.CommentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<List<CommentDTO>> createComments(@RequestBody List<CommentDTO> commentDTOList) {
        List<Comment> commentEntityList = commentDTOList.stream()
                .map(commentDTO -> modelMapper.map(commentDTO, Comment.class))
                .collect(Collectors.toList());

        List<Comment> createdComments = commentService.saveOrUpdateAllComments(commentEntityList);

        List<CommentDTO> responseCommentDTOs = createdComments.stream()
                .map(createdComment -> modelMapper.map(createdComment, CommentDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.created(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{postId}")
                        .build()
                        .toUri()
        ).body(responseCommentDTOs);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDTO>> getAllComments(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        List<CommentDTO> commentDTOs = comments.stream()
                .map(comment -> modelMapper.map(comment, CommentDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(commentDTOs);
    }
}

