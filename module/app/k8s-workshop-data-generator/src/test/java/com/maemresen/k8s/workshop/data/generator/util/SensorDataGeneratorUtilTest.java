package com.maemresen.k8s.workshop.data.generator.util;

import com.maemresen.lib.message.dto.SensorData;
import com.maemresen.lib.message.dto.SensorType;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SensorDataGeneratorUtilTest {

  private static final String LOCATION_NAME = "Location";
  private static final String DEVICE_NAME = "Device";
  private static final String SENSOR_NAME = "Sensor";
  private static final SensorType TEMPERATURE_SENSOR_TYPE = SensorType.TEMP;
  private static final SensorType HUMIDITY_SENSOR_TYPE = SensorType.HUMIDITY;

  @Test
  void generateRandomSensorData_shouldReturnNonNullSensorData() {
    final SensorData sensorData = SensorDataGeneratorUtil.generateRandomSensorData(LOCATION_NAME, DEVICE_NAME, SENSOR_NAME, TEMPERATURE_SENSOR_TYPE);
    assertThat(sensorData).isNotNull();
  }

  @Test
  void generateRandomSensorData_shouldGenerateTemperatureWithinRange() {
    SensorData sensorData = SensorDataGeneratorUtil.generateRandomSensorData(LOCATION_NAME, DEVICE_NAME, SENSOR_NAME, TEMPERATURE_SENSOR_TYPE);
    assertThat(sensorData.getValue()).isBetween(SensorDataGeneratorUtil.TEMP_MIN_VALUE, SensorDataGeneratorUtil.TEMP_MAX_VALUE);
  }

  @Test
  void generateRandomSensorData_shouldGenerateHumidityWithinRange() {
    final SensorData sensorData = SensorDataGeneratorUtil.generateRandomSensorData(LOCATION_NAME, DEVICE_NAME, SENSOR_NAME, HUMIDITY_SENSOR_TYPE);
    assertThat(sensorData.getValue()).isBetween(SensorDataGeneratorUtil.HUMIDITY_MIN_VALUE, SensorDataGeneratorUtil.HUMIDITY_MAX_VALUE);
  }
}
