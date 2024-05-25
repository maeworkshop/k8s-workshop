package com.maemresen.k8s.workshop.data.generator.config.props;

import com.maemresen.lib.message.dto.SensorType;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ConfigurationProperties(prefix = "app.generator.constraint")
@Configuration
public class GeneratorConstraintProps {
  private Map<SensorType, Double> minValues;
  private Map<SensorType, Double> maxValues;
}
