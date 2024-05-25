package com.maemresen.k8s.workshop.data.generator.itest;

import com.maemresen.k8s.workshop.data.generator.messaging.SensorDataProducer;
import com.maemresen.k8s.workshop.messaging.starter.MessagingProps;
import com.maemresen.lib.message.dto.SensorData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.time.Duration;

import static org.awaitility.Awaitility.await;

@Testcontainers
@SpringBootTest
class SensorDataProducerIT {

  private static final DockerImageName image = DockerImageName.parse("confluentinc/cp-kafka:7.6.1");

  @Container
  private static KafkaContainer container = new KafkaContainer(image);


  @DynamicPropertySource
  static void kafkaProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.kafka.bootstrap-servers", () -> container.getBootstrapServers());
  }

  @Autowired
  private MessagingProps messagingProps;

  @Autowired
  private SensorDataProducer sensorDataProducer;

  @Autowired
  private KafkaTemplate<String, SensorData> kafkaTemplate;

  @Test
  void test1() throws IOException {
    final var objectMapper = new ObjectMapper();
    final var inputStream = getClass().getResourceAsStream("/sensor-data-1.json");
    final var sensorData = objectMapper.readValue(inputStream, SensorData.class);

    final var result = sensorDataProducer.sendMessage("KEY", sensorData);
    await().atMost(Duration.ofSeconds(30))
            .until(result::isDone);
  }
}