package com.maemresen.k8s.workshop.data.generator.service;

import com.maemresen.k8s.workshop.data.generator.config.props.GeneratorConstraintProps;
import com.maemresen.lib.message.dto.Device;
import com.maemresen.lib.message.dto.Sensor;
import com.maemresen.lib.message.dto.SensorData;
import com.maemresen.lib.message.dto.SensorType;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SensorDataGeneratorService {

  private final GeneratorConstraintProps generatorConstraintProps;
  private final Random random = new SecureRandom();

  public SensorData generateRandomSensorData(
      final String locationName,
      final String deviceName,
      final String sensorName,
      final SensorType sensorType) {
    if (sensorType == null) {
      throw new IllegalArgumentException("SensorType cannot be null");
    }

    final Device device = Device.newBuilder()
        .setDeviceId(deviceName)
        .setLocation(locationName)
        .build();

    final Sensor sensor = Sensor.newBuilder()
        .setSensorId(sensorName)
        .setType(sensorType)
        .build();

    return SensorData.newBuilder()
        .setDevice(device)
        .setSensor(sensor)
        .setTimestamp(System.currentTimeMillis())
        .setValue(getRandomValueBySensorType(sensorType))
        .build();
  }

  private double getRandomValueBySensorType(final SensorType sensorType) {
    final double minValue = getMinValueBySensorType(sensorType);
    final double maxValue = getMaxValueBySensorType(sensorType);
    return random.nextDouble() * (maxValue - minValue) + minValue;
  }

  private double getMinValueBySensorType(final SensorType sensorType) {
    final Map<SensorType, Double> minValues = generatorConstraintProps.getMinValues();
    if (minValues.containsKey(sensorType)) {
      return minValues.get(sensorType);
    }

    throw new IllegalArgumentException("Sensor type " + sensorType + " is not supported");
  }

  private double getMaxValueBySensorType(final SensorType sensorType) {
    final Map<SensorType, Double> maxValues = generatorConstraintProps.getMaxValues();
    if (maxValues.containsKey(sensorType)) {
      return maxValues.get(sensorType);
    }

    throw new IllegalArgumentException("Sensor type " + sensorType + " is not supported");
  }
}
