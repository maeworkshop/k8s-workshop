package com.maemresen.k8s.workshop.messaging.starter;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.ConsumerFactory;

@RequiredArgsConstructor
public abstract class BaseConsumer<K, V> {

    private final String topic;
    private final ConsumerFactory<K, V> consumerFactory;

    @KafkaListener(topics = "#{@baseConsumer.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(final ConsumerRecord<K, V> consumerRecord) {
        processMessage(consumerRecord);
    }

    protected abstract void processMessage(final ConsumerRecord<K, V> consumerRecord);
}