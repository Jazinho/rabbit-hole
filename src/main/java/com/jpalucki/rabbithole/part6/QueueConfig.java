package com.jpalucki.rabbithole.part6;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

  @Bean
  public DirectExchange exchange() {
    return new DirectExchange("rpc");
  }

  @Bean
  public Queue fibQueue() {
    return new AnonymousQueue();
  }

  @Bean
  public Queue sqrQueue() {
    return new AnonymousQueue();
  }

  @Bean
  public Queue logQueue() { return new AnonymousQueue(); }

  @Bean
  public Binding logBinding(DirectExchange direct, Queue logQueue) {
    return BindingBuilder
      .bind(logQueue)
      .to(direct)
      .with("log");
  }

  @Bean
  public Binding sqrBinding(DirectExchange direct, Queue sqrQueue) {
    return BindingBuilder
      .bind(sqrQueue)
      .to(direct)
      .with("sqr");
  }

  @Bean
  public Binding fibBinding(DirectExchange direct, Queue fibQueue) {
    return BindingBuilder
      .bind(fibQueue)
      .to(direct)
      .with("fib");
  }
}
