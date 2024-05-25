package com.maemresen.k8s.workshop.messaging.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingAutoConfiguration {

  @ConfigurationProperties(prefix = "app.messaging")
  @ConditionalOnMissingBean
  @Bean
  public MessagingProps messagingProps() {
    return new MessagingProps();
  }
}
