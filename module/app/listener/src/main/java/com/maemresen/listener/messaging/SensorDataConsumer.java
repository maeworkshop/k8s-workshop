package com.maemresen.listener.messaging;

import com.maemresen.lib.message.dto.SensorData;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SensorDataConsumer implements Consumer<Message<SensorData>> {

  @Override
  public void accept(final Message<SensorData> message) {
    log.info("Received sensor data: {}", message);
  }
}
