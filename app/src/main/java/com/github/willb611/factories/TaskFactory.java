package com.github.willb611.factories;

import com.github.willb611.config.TaskProperties;
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
  private final TaskProperties taskProperties;
  private final AtomicInteger counter = new AtomicInteger(0);
  private final Random random = new Random();

  public SlowHttpRequestTask createTask() {
    String description = "task-" + counter.getAndIncrement();
    boolean taskMakesApiCall = true;
    if (taskProperties.isOnlySomeTasksMakeApiCall()) {
      taskMakesApiCall = random.nextBoolean();
    }
    return SlowHttpRequestTask.builder()
        .requiresBlockingIO(taskMakesApiCall)
        .taskDescription(description)
        .helloClient(helloClient)
        .integratesWithFJPManagedBlock(taskProperties.isTaskIntegratesWithFJPBlockingMode())
        .build();
  }
}
