package com.compass.statemanegement.messaging;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @JmsListener(destination = "CREATED")
    public void receiveCreatedMessage(String message) {
        System.out.println("Received message: " + message);
    }

    @JmsListener(destination = "POST_FIND")
    public void receivePostFindMessage(String message) {
        System.out.println("Received message: " + message);
    }
    @JmsListener(destination = "POST_OK")
    public void receivePostOkMessage(String message) {
        System.out.println("Received message: " + message);
    }
    @JmsListener(destination = "COMMENTS_FIND")
    public void receiveCommentsFindMessage(String message) {
        System.out.println("Received message: " + message);
    }
    @JmsListener(destination = "COMMENTS_OK")
    public void receiveCommentsOkMessage(String message) {
        System.out.println("Received message: " + message);
    }
    @JmsListener(destination = "ENABLED")
    public void receiveEnabledMessage(String message) {
        System.out.println("Received message: " + message);
    }

    @JmsListener(destination = "DISABLED")
    public void receiveDisabledMessage(String message) {
        System.out.println("Received message: " + message);
    }

    @JmsListener(destination = "UPDATING")
    public void receiveUpdatingMessage(String message) {
        System.out.println("Received message: " + message);
    }

    @JmsListener(destination = "FAILED")
    public void receiveFailedMessage(String message) {
        System.out.println("Received message: " + message);
    }

}
