package com.jpalucki.rabbithole.part2;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class WorkloadSender {

  @Autowired
  private RabbitTemplate template;

  @Autowired
  private Queue queue;

  @Value("${rabbit.part-2.workload-range}")
  private int workloadRange;

  AtomicInteger count = new AtomicInteger(0);

  @Scheduled(fixedDelay = 3000, initialDelay = 500)
  public void send() {
    int workload = ThreadLocalRandom.current().nextInt(workloadRange) + 1;
    String message = "Hello World" + ".".repeat(workload) + count.incrementAndGet();
    template.convertAndSend(queue.getName(), message);
    System.out.println(" [x] Sent '" + message + "' to queue '" + queue.getName() + "'");
  }
}
