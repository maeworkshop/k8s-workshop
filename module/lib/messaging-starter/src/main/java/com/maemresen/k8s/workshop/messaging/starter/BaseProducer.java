package com.maemresen.k8s.workshop.messaging.starter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Slf4j
public abstract class BaseProducer<K, V> {
    private final Topic topic;
    private final MessagingProps messagingProps;
    private final KafkaTemplate<K, V> kafkaTemplate;

    public CompletableFuture<SendResult<K, V>> sendMessage(final K key, final V value) {
        try {
            final var topicProps = messagingProps.getTopic(topic);
            final var topicName = topicProps.name();
            log.debug("Producing message to topic {} with key {} and value {}", topicName, key, value);
            return kafkaTemplate.send(topicName, key, value);
        } catch (Exception e) {
            log.error("Failed to send message to topic {}", topic, e);
            throw new MessagingException("Failed to send message to topic " + topic, e);
        }
    }
}