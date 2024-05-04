package com.maemresen.k8s.workshop.listener.props;

import com.maemresen.k8s.workshop.listener.util.Topic;
import java.util.EnumMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "app.messaging")
@Configuration
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
