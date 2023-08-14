package com.compass.statemanegement.messaging;

import com.compass.statemanegement.dto.CommentDTO;
import com.compass.statemanegement.dto.PostDTO;
import com.compass.statemanegement.service.ExternalDataService;
import com.compass.statemanegement.service.PostStateService;
import lombok.AllArgsConstructor;
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

    @JmsListener(destination = "CREATED")
    public void receiveCreatedMessage(Long postId) {
        postStateService.stateCreated(postId);
        messageProducer.sendPostFindMessage(postId);
    }

    @JmsListener(destination = "POST_FIND")
    public void receivePostFindMessage(Long postId) {
        externalDataService.fetchPost(postId)
                .subscribe(
                        postDTO -> {
                            postStateService.statePostFind(postId);
                            messageProducer.sendPostOkMessage(postDTO);
                        },
                        error -> {
                            messageProducer.sendFailureMessage(postId);
                        }
                );

    }
    @JmsListener(destination = "POST_OK")
    public void receivePostOkMessage(PostDTO postDTO) {
        postStateService.statePostOk(postDTO);
        messageProducer.sendCommentFindMessage(postDTO.getId());
    }
    @JmsListener(destination = "COMMENTS_FIND")
    public void receiveCommentsFindMessage(Long postId) {
        externalDataService.fetchComments(postId)
                .buffer(5)
                .flatMap(commentsBatch -> {
                    return processCommentsBatch(postId, commentsBatch);
                })
                .subscribe(
                        commentsList -> {
                            messageProducer.sendCommentOkMessage(commentsList);
                        },
                        error -> {
                            messageProducer.sendFailureMessage(postId);
                        }
                );
    }
    @JmsListener(destination = "COMMENTS_OK")
    public void receiveCommentsOkMessage(List<CommentDTO> commentsList) {
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
            postStateService.stateCommentsFind(postId, commentsBatch);
            return Mono.just(commentsBatch);
        }
        return Mono.empty();
    }

}
