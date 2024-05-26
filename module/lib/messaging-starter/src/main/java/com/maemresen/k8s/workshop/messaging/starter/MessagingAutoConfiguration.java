package com.maemresen.k8s.workshop.messaging.starter;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class MessagingAutoConfiguration {

    @ConfigurationProperties(prefix = "app.messaging")
    @ConditionalOnMissingBean
    @Bean
    public MessagingProps messagingProps() {
        return new MessagingProps();
    }

    @Bean
    public KafkaAdmin.NewTopics topics() {
        return new KafkaAdmin.NewTopics(messagingProps().getTopics()
                .values()
                .stream()
                .map(this::newTopic)
                .toArray(NewTopic[]::new));

    }

    private NewTopic newTopic(final MessagingProps.TopicProps topicProps) {
        return TopicBuilder.name(topicProps.name())
                .build();
    }
}
