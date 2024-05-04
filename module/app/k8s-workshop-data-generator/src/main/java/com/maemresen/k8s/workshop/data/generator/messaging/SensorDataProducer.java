package com.maemresen.k8s.workshop.data.generator.messaging;

import com.maemresen.lib.message.dto.SensorData;
import com.maemresen.k8s.workshop.messaging.starter.BaseMessageProducer;
import com.maemresen.k8s.workshop.messaging.starter.MessagingProps;
import com.maemresen.k8s.workshop.messaging.starter.Topic;
import org.springframework.cloud.function.cloudevent.CloudEventMessageBuilder;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class SensorDataProducer extends BaseMessageProducer<SensorData> {

  protected SensorDataProducer(
      final MessagingProps messagingProps,
      final StreamBridge streamBridge) {
    super(messagingProps, streamBridge, Topic.SENSOR_DATA);
  }

  @Override
  protected CloudEventMessageBuilder<SensorData> messageCustomizer(
      final SensorData payload,
      final CloudEventMessageBuilder<SensorData> builder) {
    return builder.setHeader(topicProps.partitionKey(), payload.getDevice().getLocation());
  }
}
