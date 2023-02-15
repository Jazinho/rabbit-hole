package com.jpalucki.rabbithole.part5;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class EventSender {

  @Autowired
  private RabbitTemplate template;

  @Autowired
  private TopicExchange topicExchange;

  private final String[] MESSAGE_TYPE = {"log", "event"};
  private final String[] MESSAGE_AREA = {"payments", "mgmt", "auth"};

  AtomicInteger count = new AtomicInteger(0);

  @Scheduled(fixedDelay = 100, initialDelay = 500)
  public void send() {
    String type = MESSAGE_TYPE[ThreadLocalRandom.current().nextInt(MESSAGE_TYPE.length)];
    String area = MESSAGE_AREA[ThreadLocalRandom.current().nextInt(MESSAGE_AREA.length)];
    String id = UUID.randomUUID().toString();
    String routingKey = type + "." + area + "." + id;
    template.convertAndSend(topicExchange.getName(), routingKey, routingKey);
    System.out.println(count.getAndIncrement() + ": " + routingKey);
  }
}
