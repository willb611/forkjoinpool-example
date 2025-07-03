package com.github.willb611.factories;

import lombok.RequiredArgsConstructor;
import com.github.willb611.client.HelloClient;
import com.github.willb611.tasks.SlowHttpRequestTask;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
public class TaskFactory {
  private final HelloClient helloClient;
  private final AtomicInteger counter = new AtomicInteger(0);

  public SlowHttpRequestTask createTask() {
    String description = "task-" + counter.getAndIncrement();
    Random r = new Random();
    return new SlowHttpRequestTask(helloClient, description, r.nextBoolean());
  }
}
