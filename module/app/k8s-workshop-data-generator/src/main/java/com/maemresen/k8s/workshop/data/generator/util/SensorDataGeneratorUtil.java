package com.maemresen.k8s.workshop.data.generator.util;

import com.maemresen.lib.message.dto.Device;
import com.maemresen.lib.message.dto.Sensor;
import com.maemresen.lib.message.dto.SensorData;
import com.maemresen.lib.message.dto.SensorType;
import java.security.SecureRandom;
import java.util.Random;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SensorDataGeneratorUtil {

  private static final Random random = new SecureRandom();

  public static final double TEMP_MIN_VALUE = 20.0;
  public static final double TEMP_MAX_VALUE = 35.0;
  public static final double HUMIDITY_MIN_VALUE = 30.0;
  public static final double HUMIDITY_MAX_VALUE = 100.0;

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
      case "TEMP" -> TEMP_MIN_VALUE + (TEMP_MAX_VALUE - TEMP_MIN_VALUE) * random.nextDouble();
      case "HUMIDITY" ->
          HUMIDITY_MIN_VALUE + (HUMIDITY_MAX_VALUE - HUMIDITY_MIN_VALUE) * random.nextDouble();
      default -> 0;
    };
  }
}
