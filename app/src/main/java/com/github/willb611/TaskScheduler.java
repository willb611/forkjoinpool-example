package com.github.willb611;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.willb611.config.TaskProperties;
import com.github.willb611.tasks.SlowHttpRequestTask;
import com.github.willb611.factories.TaskFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

@Slf4j
@RequiredArgsConstructor
@Component
public class TaskScheduler {
  @Lazy
  private final TaskProperties taskProperties;
  @Lazy
  private final ForkJoinPool forkJoinPool;
  @Lazy
  private final TaskFactory taskFactory;

  public void init() {
    triggerTasks();
  }

  private void triggerTasks() {
    List<SlowHttpRequestTask> tasks = new ArrayList<>();
    for (int i = 0; i < taskProperties.getTotalTasks(); i++) {
      tasks.add(taskFactory.createTask());
    }
    log.info("Created {} tasks, about to submit them all", tasks.size());
    List<CompletableFuture<String>> futures = new ArrayList<>();
    tasks.forEach(task -> {
      futures.add(CompletableFuture.supplyAsync(task::call, forkJoinPool));
    });
    log.info("Submitted {} tasks, waiting for results", tasks.size());
    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    log.info("Finished executing {} tasks", tasks.size());
  }

}
