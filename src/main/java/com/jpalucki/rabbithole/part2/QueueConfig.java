package com.jpalucki.rabbithole.part2;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

  @Value("${rabbit.part-2.queue-name}")
  private String queueName;

  @Bean
  public Queue queue() {
    return new Queue(queueName);
  }

  @Bean
  public Receiver receiver1() {
    return new Receiver(1);
  }

  @Bean
  public Receiver receiver2() {
    return new Receiver(2);
  }

  @Bean
  public Receiver receiver3() {
    return new Receiver(3);
  }

}
