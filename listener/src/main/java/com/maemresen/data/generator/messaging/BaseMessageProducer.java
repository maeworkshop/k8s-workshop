package com.maemresen.data.generator.messaging;

import com.maemresen.data.generator.config.props.MessagingProps;
import com.maemresen.data.generator.config.props.MessagingProps.TopicProps;
import com.maemresen.data.generator.util.Topic;
import org.springframework.cloud.function.cloudevent.CloudEventMessageBuilder;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;

public abstract class BaseMessageProducer<T> {

  protected final MessagingProps messagingProps;
  protected final StreamBridge streamBridge;
  protected final Topic topic;
  protected final TopicProps topicProps;

  protected BaseMessageProducer(
      final MessagingProps messagingProps,
      final StreamBridge streamBridge,
      final Topic topic) {
    this.messagingProps = messagingProps;
    this.streamBridge = streamBridge;
    this.topic = topic;
    this.topicProps = messagingProps.getTopic(topic);
  }

  public boolean publish(final T payload) {
    final CloudEventMessageBuilder<T> messageBuilder = CloudEventMessageBuilder.withData(payload);
    final Message<T> message = messageCustomizer(payload, messageBuilder).build();
    return streamBridge.send(topicProps.outputBindingName(), message);
  }

  public CloudEventMessageBuilder<T> messageCustomizer(final T payload,
      final CloudEventMessageBuilder<T> builder) {
    return builder;
  }
}
