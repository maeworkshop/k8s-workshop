package com.maemresen.k8s.workshop.data.generator.messaging;

import com.maemresen.k8s.workshop.messaging.starter.BaseProducer;
import com.maemresen.k8s.workshop.messaging.starter.MessagingProps;
import com.maemresen.k8s.workshop.messaging.starter.Topic;
import com.maemresen.lib.message.dto.SensorData;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SensorDataProducer extends BaseProducer<String, SensorData> {

    public SensorDataProducer(final MessagingProps messagingProps, final KafkaTemplate<String, SensorData> kafkaTemplate) {
        super(Topic.SENSOR_DATA, messagingProps, kafkaTemplate);
    }
}
