package com.maemresen.data.generator.util;

import java.security.SecureRandom;
import java.util.Random;
import com.maemresen.lib.message.dto.Device;
import com.maemresen.lib.message.dto.Sensor;
import com.maemresen.lib.message.dto.SensorData;
import com.maemresen.lib.message.dto.SensorType;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SensorDataGeneratorUtil {

  private static final Random random = new SecureRandom();

  public static SensorData generateRandomSensorData(
      final String locationName,
      final String deviceName,
      final String sensorName,
      final SensorType sensorType) {
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
        .setValue(generateRandomValue(sensor.getType().toString()))
        .build();
  }

  private static double generateRandomValue(String sensorType) {
    return switch (sensorType) {
      case "TEMP" -> 20 + 15 * random.nextDouble();  // Temperature from 20 to 35
      case "HUMIDITY" -> 30 + 70 * random.nextDouble();  // Humidity from 30% to 100%
      default -> 0;
    };
  }
}
