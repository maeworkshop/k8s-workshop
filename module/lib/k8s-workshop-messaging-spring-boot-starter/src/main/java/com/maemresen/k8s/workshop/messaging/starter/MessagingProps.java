package com.maemresen.k8s.workshop.messaging.starter;

import java.util.EnumMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessagingProps {

  private EnumMap<Topic, TopicProps> topics;

  public record TopicProps(
      String outputBindingName,
      String topicName,
      String partitionKey,
      Map<String, String> extra) {

  }

  public TopicProps getTopic(final Topic topic) {
    return topics.getOrDefault(topic, null);
  }
}
