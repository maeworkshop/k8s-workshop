package com.maemresen.data.generator.messaging;

import com.maemresen.data.generator.config.props.MessagingProps;
import com.maemresen.data.generator.util.Topic;
import com.maemresen.lib.message.dto.SensorData;
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
