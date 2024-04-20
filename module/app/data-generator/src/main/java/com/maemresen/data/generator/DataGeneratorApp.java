package com.maemresen.data.generator;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class DataGeneratorApp {

  public static void main(String[] args) {
    new SpringApplicationBuilder(DataGeneratorApp.class)
        .web(WebApplicationType.NONE)
        .run(args);
  }
}
