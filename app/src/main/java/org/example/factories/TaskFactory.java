package org.example.factories;

import lombok.RequiredArgsConstructor;
import org.example.client.HelloClient;
import org.example.config.SleepHelper;
import org.example.config.tasks.Task;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
public class TaskFactory {
  private final SleepHelper sleepHelper;
  private final HelloClient helloClient;

  private final Random random = new Random();
  private final AtomicInteger counter = new AtomicInteger(0);

  public Task createTask() {
    String description = "task-" + counter.getAndIncrement();
    return new Task(helloClient, sleepHelper.getTimeToSleep(), description);
  }
}
