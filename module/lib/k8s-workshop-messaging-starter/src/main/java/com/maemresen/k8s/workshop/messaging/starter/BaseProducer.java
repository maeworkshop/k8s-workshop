package com.maemresen.k8s.workshop.messaging.starter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
@Slf4j
public class BaseProducer<K, V> {
    private final Topic topic;
    private final MessagingProps messagingProps;
    private final KafkaTemplate<K, V> kafkaTemplate;

    public void sendMessage(final K key, final V value) {
        try {
            final var topicProps = messagingProps.getTopic(topic);
            final var topicName = topicProps.name();
            kafkaTemplate.send(topicName, key, value);
            log.info("Producing message to topic {} with key {} and value {}", topicName, key, value);
        } catch (Exception e) {
            log.error("Failed to send message to topic {}", topic, e);
            throw new MessagingException("Failed to send message to topic " + topic, e);
        }
    }
}