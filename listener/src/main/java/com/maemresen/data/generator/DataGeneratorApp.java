package com.maemresen.data.generator;

import com.maemresen.data.generator.messaging.SensorDataProducer;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class DataGeneratorApp implements CommandLineRunner {

  public static void main(String[] args) {
    new SpringApplicationBuilder(DataGeneratorApp.class)
        .web(WebApplicationType.NONE)
        .run(args);
  }

  private final SensorDataProducer sensorDataProducer;

  @Override
  public void run(String... args) {
    for (int i = 0; i < 10; i++) {
      final SensorData sensorData = new SensorData();
      sensorData.setDeviceId("Device" + i);
      sensorData.setTimestamp(System.currentTimeMillis());
      sensorDataProducer.publish(sensorData);
    }
  }

  @Bean
  public Consumer<Message<SensorData>> sensorDataConsumer() {

    return message -> {
      int partition = (int) message.getHeaders().get(KafkaHeaders.RECEIVED_PARTITION);
      final SensorData payload = message.getPayload();
      final Instant instant = Instant.ofEpochMilli(payload.getTimestamp());
      log.info("Consumed {}-{}-{}", partition, payload.getDeviceId(),
          DateTimeFormatter.ISO_DATE_TIME.format(
              LocalDateTime.ofInstant(instant, ZoneId.systemDefault())));
    };
  }
}
