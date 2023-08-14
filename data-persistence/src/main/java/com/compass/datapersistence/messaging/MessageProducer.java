package com.compass.datapersistence.messaging;


import com.compass.datapersistence.entity.Comment;
import com.compass.datapersistence.entity.Post;
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

    public void sendPostOkMessage(Post post) {
        jmsTemplate.convertAndSend("POST_OK",post);
    }

    public void sendCommentFindMessage(Long postId) {
        jmsTemplate.convertAndSend("COMMENT_FIND", postId);
    }

    public void sendCommentOkMessage(List<Comment> commentsList) {
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
