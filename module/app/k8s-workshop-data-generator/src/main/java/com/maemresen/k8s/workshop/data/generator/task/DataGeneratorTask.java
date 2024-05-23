package com.maemresen.k8s.workshop.data.generator.task;

import com.maemresen.k8s.workshop.data.generator.config.props.GeneratorSensorDataProps;
import com.maemresen.k8s.workshop.data.generator.messaging.SensorDataProducer;
import com.maemresen.k8s.workshop.data.generator.service.SensorDataGeneratorService;
import com.maemresen.lib.message.dto.SensorData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class DataGeneratorTask {

  private final GeneratorSensorDataProps dataGeneratorProps;
  private final SensorDataGeneratorService sensorDataGeneratorService;
    private final SensorDataProducer sensorDataProducer;

  @Scheduled(cron = "0/2 * * * * ?")
  public void produceSensorData() {

    dataGeneratorProps.getLocations().forEach(location -> location.devices().forEach(
        device -> device.sensors()
            .forEach(sensor -> {
              final SensorData sensorData = sensorDataGeneratorService.generateRandomSensorData(
                  location.name(),
                  device.name(),
                  sensor.name(),
                  sensor.type());
                sensorDataProducer.sendMessage(device.name(), sensorData);
            })));
  }

}
