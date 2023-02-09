package com.jpalucki.rabbithole.part1;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DummySender {

  @Autowired
  private RabbitTemplate template;

  @Value("rabbit.queues.tut-1-queue-1.name")
  private String queueName;

  @Scheduled(fixedDelay = 1000, initialDelay = 500)
  public void send() {
    String message = "Hello World!";
    template.convertAndSend(queueName, message);
    System.out.println(" [x] Sent '" + message + "'");
  }
}
