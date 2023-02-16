package com.jpalucki.rabbithole.part6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RabbitHoleApplication {

  public static void main(String[] args) {
    SpringApplication.run(RabbitHoleApplication.class, args);
  }
}
