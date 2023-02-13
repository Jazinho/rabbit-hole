package com.jpalucki.rabbithole.part3;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;

public class Receiver {

  @RabbitListener(queues = "#{anonQueue1.name}")
  public void receive1(String in) throws InterruptedException {
    receive(in, 1);
  }

  @RabbitListener(queues = "#{anonQueue2.name}")
  public void receive2(String in) throws InterruptedException {
    receive(in, 2);
  }

  public void receive(String in, int instance) throws InterruptedException {
    StopWatch watch = new StopWatch();
    watch.start();
    System.out.println(" [  X] instance " + instance + " [x] Received '" + in + "'");
    doWork(in);
    watch.stop();
    System.out.println(" [  X] instance " + instance + " [x] Done in " + (int) watch.getTotalTimeSeconds() + "s");
  }

  private void doWork(String in) throws InterruptedException {
    Thread.sleep(1100L);
  }
}
