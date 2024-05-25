package com.maemresen.k8s.workshop.listener.messaging;

import com.maemresen.k8s.workshop.messaging.starter.BaseConsumer;
import com.maemresen.k8s.workshop.messaging.starter.MessagingProps;
import com.maemresen.k8s.workshop.messaging.starter.Topic;
import com.maemresen.lib.message.dto.SensorData;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SensorDataConsumer extends BaseConsumer<String, SensorData> {

  public SensorDataConsumer(final MessagingProps messagingProps) {
    super(Topic.SENSOR_DATA, messagingProps);
  }

  @Override
  protected void processMessage(final ConsumerRecord<String, SensorData> consumerRecord) {
    log.info("Received sensor data with key {} and value {}", consumerRecord.key(), consumerRecord.value());
  }
}
