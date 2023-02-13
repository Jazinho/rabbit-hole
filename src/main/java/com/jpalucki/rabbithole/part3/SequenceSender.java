package com.jpalucki.rabbithole.part3;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class SequenceSender {

  @Autowired
  private RabbitTemplate template;

  @Autowired
  private FanoutExchange fanoutExchange;

  AtomicInteger count = new AtomicInteger(0);

  @Scheduled(fixedDelay = 1000, initialDelay = 500)
  public void send() {
    int countNum = count.incrementAndGet();
    String message = "Hello World, no. " + countNum;
    template.convertAndSend(fanoutExchange.getName(), "", message);
    System.out.println(" [-> ] Sent '" + message + "' to exchange '" + fanoutExchange.getName() + "'");
  }
}
