package com.jpalucki.rabbithole.part6;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class FunctionInputSender {

  @Autowired
  private RabbitTemplate template;

  @Autowired
  private DirectExchange directExchange;

  private final String[] FUN_TYPE = {"fib", "sqr", "log"};

  @Scheduled(fixedDelay = 3000, initialDelay = 500)
  public void send() {
    String function = FUN_TYPE[ThreadLocalRandom.current().nextInt(FUN_TYPE.length)];
    Integer input = ThreadLocalRandom.current().nextInt(10);
    Integer result = (Integer) template.convertSendAndReceive(directExchange.getName(), function, input);
    System.out.println("[FUNCTION]: " + function + "  for input: " + input + "  returned: " + result);
  }
}
