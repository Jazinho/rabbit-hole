package com.jpalucki.rabbithole.part3;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

  @Value("${rabbit.part-3.exchange-name}")
  private String exchangeName;

  @Bean
  public FanoutExchange fanout() {
    return new FanoutExchange(exchangeName);
  }

  @Bean
  public Queue anonQueue1() {
    return new AnonymousQueue();
  }

  @Bean
  public Queue anonQueue2() {
    return new AnonymousQueue();
  }

  @Bean
  public Receiver receivers() {
    return new Receiver();
  }

  @Bean
  public Binding binding1(FanoutExchange fanout, Queue anonQueue1) {
    return BindingBuilder.bind(anonQueue1).to(fanout);
  }

  @Bean
  public Binding binding2(FanoutExchange fanout, Queue anonQueue2) {
    return BindingBuilder.bind(anonQueue2).to(fanout);
  }
}
