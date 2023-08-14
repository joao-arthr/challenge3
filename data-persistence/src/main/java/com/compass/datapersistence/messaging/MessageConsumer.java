package com.compass.datapersistence.messaging;

import com.compass.datapersistence.entity.Comment;
import com.compass.datapersistence.entity.Post;
import com.compass.datapersistence.service.*;
import com.compass.datapersistence.dto.CommentDTO;
import com.compass.datapersistence.service.PostStateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;


@Component
@AllArgsConstructor
public class MessageConsumer {
    private ExternalDataService externalDataService;
    private MessageProducer messageProducer;
    private PostStateService postStateService;
    private PostService postService;
    private ObjectMapper objectMapper;

    @JmsListener(destination = "CREATED")
    public void receiveCreatedMessage(Long postId) {
        postStateService.stateCreated(postId);
        messageProducer.sendPostFindMessage(postId);
    }

    @JmsListener(destination = "POST_FIND")
    public void receivePostFindMessage(Long postId) {
        postStateService.statePostFind(postId);
        var post = postService.getPostById(postId);
        externalDataService.fetchPost(postId)
                .subscribe(
                        postDTO -> {
                            post.setTitle(postDTO.getTitle());
                            post.setBody(postDTO.getBody());
                            messageProducer.sendPostOkMessage(post);
                        }
                );

    }
    @JmsListener(destination = "POST_OK")
    public void receivePostOkMessage(Post post) {
        postStateService.statePostOk(post);
        messageProducer.sendCommentFindMessage(post.getId());
    }
    @JmsListener(destination = "COMMENTS_FIND")
    public void receiveCommentsFindMessage(Long postId) {
        externalDataService.fetchComments(postId)
                .buffer(5)
                .flatMap(commentsBatch -> {
                    return processCommentsBatch(postId, commentsBatch);
                })
                .subscribe(
                        commentsListDTO -> {
                            var commentsList = commentsListDTO.stream()
                                    .map(commentDTO -> {
                                        Comment comment = new Comment();
                                        comment.setId(commentDTO.getId());
                                        comment.setBody(commentDTO.getBody());
                                        return comment;
                                    })
                                    .toList();
                            messageProducer.sendCommentOkMessage(commentsList);
                        },
                        error -> {
                            messageProducer.sendFailureMessage(postId);
                        }
                );
    }
    @JmsListener(destination = "COMMENTS_OK")
    public void receiveCommentsOkMessage(List<Comment> commentsList) {
        postStateService.stateCommentsOk(commentsList);
        messageProducer.sendEnabledMessage(commentsList.get(0).getId());
    }
    @JmsListener(destination = "ENABLED")
    public void receiveEnabledMessage(Long postId) {
        postStateService.stateEnabled(postId);
    }

    @JmsListener(destination = "DISABLED")
    public void receiveDisabledMessage(Long postId) {
        postStateService.stateDisabled(postId);
    }

    @JmsListener(destination = "UPDATING")
    public void receiveUpdatingMessage(Long postId) {
        postStateService.stateUpdating(postId);
        messageProducer.sendPostFindMessage(postId);
    }

    @JmsListener(destination = "FAILED")
    public void receiveFailedMessage(Long postId) {
        postStateService.stateFailure(postId);
        messageProducer.sendDisabledMessage(postId);
    }

    private Mono<List<CommentDTO>> processCommentsBatch(Long postId, List<CommentDTO> commentsBatch) {
        if (!commentsBatch.isEmpty()) {
            var listComments = commentsBatch.stream()
                    .map(commentDTO -> {
                        Comment comment = new Comment();
                        comment.setId(commentDTO.getId());
                        comment.setBody(commentDTO.getBody());
                        return comment;
                    })
                    .toList();
            postStateService.stateCommentsFind(postId, listComments);
            return Mono.just(commentsBatch);
        }
        return Mono.empty();
    }

}
