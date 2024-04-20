package com.maemresen.listener.messaging;

import com.maemresen.lib.message.dto.SensorData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.function.Consumer;

@Slf4j
@Component
public class SensorDataConsumer implements Consumer<SensorData> {

  @Override
  public void accept(final SensorData sensorData) {
    log.info("Received sensor data: {}", sensorData);
  }
}
