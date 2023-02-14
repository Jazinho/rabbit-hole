package com.jpalucki.rabbithole.part4;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class Receiver {

  @RabbitListener(queues = "#{warmColorsQueue.name}")
  public void receiveWarm(String in) {
    System.out.println(" [R  ] received color: " + in);
  }

  @RabbitListener(queues = "#{coldColorsQueue.name}")
  public void receiveCold(String in) {
    System.out.println(" [  B] received color: " + in);
  }

  @RabbitListener(queues = "#{neutralColorsQueue.name}")
  public void receiveNeutral(String in) {
    System.out.println(" [ G ] received color: " + in);
  }
}
