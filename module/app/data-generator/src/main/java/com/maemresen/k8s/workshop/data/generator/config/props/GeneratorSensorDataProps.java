package com.maemresen.k8s.workshop.data.generator.config.props;

import com.maemresen.lib.message.dto.SensorType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "app.generator.sensor-data")
@Configuration
public class GeneratorSensorDataProps {

  private List<LocationProps> locations;

  public record LocationProps(String name, List<DeviceProps> devices) {
  }

  public record DeviceProps(String name, List<SensorProps> sensors) {

  }

  public record SensorProps(String name, SensorType type) {

  }
}
