package com.compass.statemanegement.messaging;

import com.compass.statemanegement.dto.CommentDTO;
import com.compass.statemanegement.dto.PostDTO;
import lombok.AllArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class MessageProducer {

    private final JmsTemplate jmsTemplate;

    public void sendCreatedMessage(Long postId) {
        jmsTemplate.convertAndSend("CREATED", postId);
    }

    public void sendPostFindMessage(Long postId) {
        jmsTemplate.convertAndSend("POST_FIND", postId);
    }

    public void sendPostOkMessage(PostDTO postDTO) {
        jmsTemplate.convertAndSend("POST_OK",postDTO);
    }

    public void sendCommentFindMessage(Long postId) {
        jmsTemplate.convertAndSend("COMMENT_FIND", postId);
    }

    public void sendCommentOkMessage(List<CommentDTO> commentsList) {
        jmsTemplate.convertAndSend("COMMENT_OK", commentsList);
    }

    public void sendEnabledMessage(Long postId) {
        jmsTemplate.convertAndSend("ENABLED", postId);
    }

    public void sendDisabledMessage(Long postId) {
        jmsTemplate.convertAndSend("DISABLED", postId);
    }

    public void sendFailureMessage(Long postId) {
        jmsTemplate.convertAndSend("FAILURE", postId);
    }

    public void sendUpdatingMessage(Long postId) {
        jmsTemplate.convertAndSend("UPDATING", postId);
    }
}
