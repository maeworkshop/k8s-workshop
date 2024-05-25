package com.maemresen.k8s.workshop.listener;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.maemresen")
@SpringBootApplication
public class ListenerApp {

  public static void main(String[] args) {
    new SpringApplicationBuilder(ListenerApp.class)
        .web(WebApplicationType.NONE)
        .run(args);
  }
}
