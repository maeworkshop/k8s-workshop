package com.maemresen.k8s.workshop.messaging.starter;

public class MessagingException extends RuntimeException {

    public MessagingException(String message, Throwable cause) {
        super(message, cause);
    }
}
