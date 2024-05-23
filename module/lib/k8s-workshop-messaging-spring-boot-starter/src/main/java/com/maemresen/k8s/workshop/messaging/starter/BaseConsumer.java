package com.maemresen.k8s.workshop.messaging.starter;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

@RequiredArgsConstructor
public abstract class BaseConsumer<K, V> {

    private final Topic topic;
    private final MessagingProps messagingProps;

    @KafkaListener(topics = "#{__listener.getTopicName()}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(final ConsumerRecord<K, V> consumerRecord) {
        processMessage(consumerRecord);
    }

    public String getTopicName() {
        final var topicProps = messagingProps.getTopic(topic);
        return topicProps.name();
    }

    protected abstract void processMessage(final ConsumerRecord<K, V> consumerRecord);
}