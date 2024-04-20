package com.maemresen.data.generator.task;

import com.maemresen.data.generator.config.props.SensorDataGeneratorProps;
import com.maemresen.data.generator.messaging.SensorDataProducer;
import com.maemresen.data.generator.util.SensorDataGeneratorUtil;
import com.maemresen.lib.message.dto.SensorData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class DataGeneratorTask {

  private final SensorDataGeneratorProps dataGeneratorProps;
  private final SensorDataProducer sensorDataProducer;

  @Scheduled(cron = "0/2 * * * * ?")
  public void produceSensorData() {

    dataGeneratorProps.getLocations().forEach(location -> location.devices().forEach(
        device -> device.sensors()
            .forEach(sensor -> {
              final SensorData sensorData = SensorDataGeneratorUtil.generateRandomSensorData(
                  location.name(),
                  device.name(),
                  sensor.name(),
                  sensor.type());
              sensorDataProducer.publish(sensorData);
            })));
  }

}
