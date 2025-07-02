package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.config.tasks.ExampleConfig;
import org.example.config.tasks.Task;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

@Slf4j
public class Example {
  private final ForkJoinPool forkJoinPool;
  private final ExampleConfig exampleConfig;

  public Example(ExampleConfig exampleConfig) {
    this.forkJoinPool = ForkJoinPoolConfig.createForkJoinPool();
    this.exampleConfig = exampleConfig;
  }

  public void start() {
    triggerTasks();
  }

  private void triggerTasks() {
    int counter = 0;
    List<Task> tasks = new ArrayList<>();
    Random random = new Random();
    for (int i = 0; i < exampleConfig.getTotalTasks(); i++) {
      long amount = exampleConfig.getMinSleepPerTask() + random.nextInt(exampleConfig.getMinSleepPerTask());
      Duration duration = Duration.of(amount, exampleConfig.getSleepTimeUnit());
      String description = "task-" + counter++;
      tasks.add(new Task(duration, description));
    }
    List<CompletableFuture<String>> futures = new ArrayList<>();
    tasks.forEach(task -> {
      futures.add(CompletableFuture.supplyAsync(task::call, forkJoinPool));
    });
    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    log.info("Finished executing {} tasks", tasks.size());
  }

}
