package com.maemresen.k8s.workshop.data.generator.util;

import static org.assertj.core.api.Assertions.assertThat;

import com.maemresen.lib.message.dto.SensorData;
import com.maemresen.lib.message.dto.SensorType;
import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

class SensorDataGeneratorUtilTest {

  private static final String LOCATION_NAME = "Location";
  private static final String DEVICE_NAME = "Device";
  private static final String SENSOR_NAME = "Sensor";

  @ParameterizedTest
  @ArgumentsSource(SensorTypeArgumentProvider.class)
  void generateRandomSensorData_shouldReturnNonNullSensorData(final SensorType sensorType) {
    final SensorData sensorData = SensorDataGeneratorUtil.generateRandomSensorData(LOCATION_NAME,
        DEVICE_NAME, SENSOR_NAME, sensorType);
    assertThat(sensorData).isNotNull();
  }

  @ParameterizedTest
  @ArgumentsSource(SensorTypeArgumentProvider.class)
  void generateRandomSensorData_shouldGenerateTemperatureWithinRange(final SensorType sensorType) {
    SensorData sensorData = SensorDataGeneratorUtil.generateRandomSensorData(LOCATION_NAME,
        DEVICE_NAME, SENSOR_NAME, sensorType);
    assertThat(sensorData.getValue()).isBetween(SensorDataGeneratorUtil.TEMP_MIN_VALUE,
        SensorDataGeneratorUtil.TEMP_MAX_VALUE);
  }

  @ParameterizedTest
  @ArgumentsSource(SensorTypeArgumentProvider.class)
  void generateRandomSensorData_shouldGenerateHumidityWithinRange(final SensorType sensorType) {
    final SensorData sensorData = SensorDataGeneratorUtil.generateRandomSensorData(LOCATION_NAME,
        DEVICE_NAME, SENSOR_NAME, sensorType);
    assertThat(sensorData.getValue()).isBetween(SensorDataGeneratorUtil.HUMIDITY_MIN_VALUE,
        SensorDataGeneratorUtil.HUMIDITY_MAX_VALUE);
  }

  static class SensorTypeArgumentProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
      return Arrays.stream(SensorType.values()).map(Arguments::of);
    }
  }
}
