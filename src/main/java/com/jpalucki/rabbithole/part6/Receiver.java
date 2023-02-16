package com.jpalucki.rabbithole.part6;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

  @RabbitListener(queues = "#{logQueue.name}")
  public int receiveLogReq(int in) {
    System.out.println("LOG for " + in);
    return (int) Math.log(in);
  }

  @RabbitListener(queues = "#{sqrQueue.name}")
  public int receiveSqrReq(int in) {
    System.out.println("SQR for " + in);
    return (int) Math.sqrt(in);
  }

  @RabbitListener(queues = "#{fibQueue.name}")
  public int receiveFibReq(int in) {
    System.out.println("FIB for " + in);
    return fib(in);
  }

  private int fib(int n) {
    return n == 0 ? 0 : n == 1 ? 1 : (fib(n - 1) + fib(n - 2));
  }
}
