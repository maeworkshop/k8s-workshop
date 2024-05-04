package com.maemresen.k8s.workshop.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class ListenerApp {

  public static void main(String[] args) {
    new SpringApplicationBuilder(ListenerApp.class)
        .web(WebApplicationType.NONE)
        .run(args);
  }
}
