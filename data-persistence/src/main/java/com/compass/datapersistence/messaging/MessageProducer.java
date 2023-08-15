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

    public void sendPostOkMessage(Long postId) {
        jmsTemplate.convertAndSend("POST_OK",postId);
    }

    public void sendCommentFindMessage(Long postId) {
        jmsTemplate.convertAndSend("COMMENTS_FIND", postId);
    }

    public void sendCommentOkMessage(Long postId) {
        jmsTemplate.convertAndSend("COMMENTS_OK", postId);
    }

    public void sendEnabledMessage(Long postId) {
        jmsTemplate.convertAndSend("ENABLED", postId);
    }

    public void sendDisabledMessage(Long postId) {
        jmsTemplate.convertAndSend("DISABLED", postId);
    }

    public void sendFailureMessage(Long postId) {
        jmsTemplate.convertAndSend("FAILED", postId);
    }

    public void sendUpdatingMessage(Long postId) {
        jmsTemplate.convertAndSend("UPDATING", postId);
    }
}
