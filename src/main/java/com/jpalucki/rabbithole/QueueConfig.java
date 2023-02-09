package com.jpalucki.rabbithole;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

  @Value("rabbit.queues.tut-1-queue-1.name")
  private String queueName;

  @Bean
  public Queue queue() {
    return new Queue(queueName);
  }
}
