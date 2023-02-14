package com.jpalucki.rabbithole.part4;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class ColorsSender {

  @Autowired
  private RabbitTemplate template;

  @Autowired
  private DirectExchange directExchange;

  private final String[] colors = {"red", "green", "blue"};

  @Scheduled(fixedDelay = 1000, initialDelay = 500)
  public void send() {
    String color = colors[ThreadLocalRandom.current().nextInt(colors.length)];
    String message = "Message with color: " + color;
    template.convertAndSend(directExchange.getName(), color, message);
  }
}
