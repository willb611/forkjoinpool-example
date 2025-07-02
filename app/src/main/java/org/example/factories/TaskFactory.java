package org.example.factories;

import lombok.RequiredArgsConstructor;
import org.example.client.HelloClient;
import org.example.tasks.SlowHttpRequestTask;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
public class TaskFactory {
  private final HelloClient helloClient;
  private final AtomicInteger counter = new AtomicInteger(0);

  public SlowHttpRequestTask createTask() {
    String description = "task-" + counter.getAndIncrement();
    return new SlowHttpRequestTask(helloClient, description);
  }
}
