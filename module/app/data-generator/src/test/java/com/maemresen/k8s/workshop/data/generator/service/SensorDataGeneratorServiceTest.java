package com.maemresen.k8s.workshop.data.generator.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.InstanceOfAssertFactories.DOUBLE;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.maemresen.k8s.workshop.data.generator.config.props.GeneratorConstraintProps;
import com.maemresen.lib.message.dto.SensorData;
import com.maemresen.lib.message.dto.SensorType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SensorDataGeneratorServiceTest {

  private static final SensorType VALID_SENSOR_TYPE = SensorType.TEMP;
  private static final double MIN_VALUE = 20.0;
  private static final double MAX_VALUE = 35.0;

  private static final Map<SensorType, Double> MIN_VALUES = Map.of(VALID_SENSOR_TYPE, MIN_VALUE);
  private static final Map<SensorType, Double> MAX_VALUES = Map.of(VALID_SENSOR_TYPE, MAX_VALUE);

  private static final Map<SensorType, Double> MIN_VALUES_WITHOUT_SENSOR_TYPE = new HashMap<>();
  private static final Map<SensorType, Double> MAX_VALUES_WITHOUT_SENSOR_TYPE = new HashMap<>();

  private static final String LOCATION_NAME = "Location";
  private static final String DEVICE_NAME = "Device";
  private static final String SENSOR_NAME = "Sensor";

  @Mock
  private GeneratorConstraintProps generatorConstraintProps;

  @InjectMocks
  private SensorDataGeneratorService sensorDataGeneratorService;

  @Test
  void generateRandomSensorData_shouldGenerateSensorValueWithinRange() {
    when(generatorConstraintProps.getMinValues()).thenReturn(MIN_VALUES);
    when(generatorConstraintProps.getMaxValues()).thenReturn(MAX_VALUES);

    final SensorData sensorData =
        sensorDataGeneratorService.generateRandomSensorData(
            LOCATION_NAME,
            DEVICE_NAME,
            SENSOR_NAME,
            VALID_SENSOR_TYPE);
    assertThat(sensorData).isNotNull()
        .extracting(SensorData::getValue).asInstanceOf(DOUBLE)
        .isBetween(MIN_VALUE, MAX_VALUE);
  }

  @Test
  void generateRandomSensorData_shouldThrowErrorOnNullSensorType() {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(
            () -> sensorDataGeneratorService.generateRandomSensorData(LOCATION_NAME, DEVICE_NAME,
                SENSOR_NAME, null));
  }

  @Test
  void generateRandomSensorData_shouldThrowErrorOnInvalidSensorType() {
    when(generatorConstraintProps.getMinValues()).thenReturn(
        MIN_VALUES_WITHOUT_SENSOR_TYPE);
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(
            () -> sensorDataGeneratorService.generateRandomSensorData(LOCATION_NAME, DEVICE_NAME,
                SENSOR_NAME, VALID_SENSOR_TYPE));

    when(generatorConstraintProps.getMinValues()).thenReturn(MIN_VALUES);
    when(generatorConstraintProps.getMaxValues()).thenReturn(MAX_VALUES_WITHOUT_SENSOR_TYPE);
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(
            () -> sensorDataGeneratorService.generateRandomSensorData(LOCATION_NAME, DEVICE_NAME,
                SENSOR_NAME, VALID_SENSOR_TYPE));
  }

}