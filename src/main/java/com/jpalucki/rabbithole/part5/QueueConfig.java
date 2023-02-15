package com.jpalucki.rabbithole.part5;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

  @Value("${rabbit.part-5.topic-name}")
  private String topicName;

  @Value("${rabbit.part-5.logs-queue-name}")
  private String logsQueueName;

  @Value("${rabbit.part-5.events-queue-name}")
  private String eventsQueueName;

  @Value("${rabbit.part-5.auth-queue-name}")
  private String authQueueName;

  @Value("${rabbit.part-5.mgmt-queue-name}")
  private String mgmtQueueName;

  @Value("${rabbit.part-5.payments-queue-name}")
  private String paymentsQueueName;

  @Bean
  public TopicExchange topic() {
    return new TopicExchange(topicName);
  }

  @Bean
  public Queue logsQueue() {
    return new Queue(logsQueueName);
  }

  @Bean
  public Queue eventsQueue() {
    return new Queue(eventsQueueName);
  }

  @Bean
  public Queue paymentsQueue() { return new Queue(paymentsQueueName); }

  @Bean
  public Queue mgmtQueue() {
    return new Queue(mgmtQueueName);
  }

  @Bean
  public Queue authQueue() {
    return new Queue(authQueueName);
  }

  @Bean
  public Binding logsBinding(TopicExchange topic, Queue logsQueue) {
    return BindingBuilder
      .bind(logsQueue)
      .to(topic)
      .with("log.#");
  }

  @Bean
  public Binding eventBinding(TopicExchange topic, Queue eventsQueue) {
    return BindingBuilder
      .bind(eventsQueue)
      .to(topic)
      .with("event.#");
  }

  @Bean
  public Binding paymentsBinding(TopicExchange topic, Queue paymentsQueue) {
    return BindingBuilder
      .bind(paymentsQueue)
      .to(topic)
      .with("*.payments.*");
  }

  @Bean
  public Binding mgmtBinding(TopicExchange topic, Queue mgmtQueue) {
    return BindingBuilder
      .bind(mgmtQueue)
      .to(topic)
      .with("*.mgmt.*");
  }

  @Bean
  public Binding authBinding(TopicExchange topic, Queue authQueue) {
    return BindingBuilder
      .bind(authQueue)
      .to(topic)
      .with("*.auth.*");
  }
}
