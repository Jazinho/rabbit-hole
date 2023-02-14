package com.jpalucki.rabbithole.part4;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

  @Value("${rabbit.part-4.exchange-name}")
  private String exchangeName;

  @Value("${rabbit.part-4.warm-colors-queue-name}")
  private String warmQueueName;

  @Value("${rabbit.part-4.cold-colors-queue-name}")
  private String coldQueueName;

  @Value("${rabbit.part-4.neutral-colors-queue-name}")
  private String neutralQueueName;

  @Bean
  public DirectExchange direct() {
    return new DirectExchange(exchangeName);
  }

  @Bean
  public Queue warmColorsQueue() {
    return new Queue(warmQueueName);
  }

  @Bean
  public Queue coldColorsQueue() {
    return new Queue(coldQueueName);
  }

  @Bean
  public Queue neutralColorsQueue() {
    return new Queue(neutralQueueName);
  }

  @Bean
  public Receiver receivers() {
    return new Receiver();
  }

  @Bean
  public Binding warmBinding(DirectExchange direct, Queue warmColorsQueue) {
    return BindingBuilder
      .bind(warmColorsQueue)
      .to(direct)
      .with("red");
  }

  @Bean
  public Binding coldBinding(DirectExchange direct, Queue coldColorsQueue) {
    return BindingBuilder
      .bind(coldColorsQueue)
      .to(direct)
      .with("blue");
  }

  @Bean
  public Binding neutralBinding(DirectExchange direct, Queue neutralColorsQueue) {
    return BindingBuilder
      .bind(neutralColorsQueue)
      .to(direct)
      .with("green");
  }
}
