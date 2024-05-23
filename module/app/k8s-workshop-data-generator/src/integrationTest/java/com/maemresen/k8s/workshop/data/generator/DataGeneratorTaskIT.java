package com.maemresen.k8s.workshop.data.generator;

import com.maemresen.k8s.workshop.data.generator.task.DataGeneratorTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest
class DataGeneratorTaskIT {

  @Autowired
  private DataGeneratorTask dataGeneratorTask;

  @Container
  private KafkaContainer kafkaContainer =
      new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.6.1"));


  @Test
  void test1() {
    dataGeneratorTask.produceSensorData();
  }
}