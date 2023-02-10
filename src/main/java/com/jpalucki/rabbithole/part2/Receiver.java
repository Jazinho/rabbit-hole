package com.jpalucki.rabbithole.part2;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;

@RabbitListener(queues = "${rabbit.part-2.queue-name}")
public class Receiver {

  private final int instance;

  public Receiver(int instance) {
    this.instance = instance;
  }

  @RabbitHandler
  public void receive(String in) throws InterruptedException {
    StopWatch watch = new StopWatch();
    watch.start();
    System.out.println("instance " + instance + " [x] Received '" + in + "'");
    doWork(in);
    watch.stop();
    System.out.println("instance " + instance + " [x] Done in " + (int) watch.getTotalTimeSeconds() + "s");
  }

  private void doWork(String in) throws InterruptedException {
    String dottedPart = in.substring(in.indexOf("."), in.lastIndexOf(".") + 1);
    int workTimeInSec = dottedPart.split("").length;
    Thread.sleep(workTimeInSec * 1000L);
  }
}
