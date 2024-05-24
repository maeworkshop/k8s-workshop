package com.maemresen.k8s.workshop.data.generator;

import com.maemresen.k8s.workshop.data.generator.task.DataGeneratorTask;
import com.maemresen.lib.message.dto.SensorData;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest
class DataGeneratorTaskIT {

  private static final DockerImageName image = DockerImageName.parse("confluentinc/cp-kafka:7.6.1");

  @Container
  private static KafkaContainer container = new KafkaContainer(image);

  @Autowired
  private DataGeneratorTask dataGeneratorTask;

  @Autowired
  private KafkaTemplate<String, SensorData> kafkaTemplate;

  @DynamicPropertySource
  static void kafkaProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.kafka.bootstrap-servers", () -> container.getBootstrapServers());
  }

  @Test
  void test1() {
    dataGeneratorTask.produceSensorData();
  }
}