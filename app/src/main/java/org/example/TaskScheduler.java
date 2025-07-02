package org.example;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.config.TaskConfig;
import org.example.config.tasks.Task;
import org.example.factories.TaskFactory;
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
  private final TaskConfig taskConfig;
  @Lazy
  private final ForkJoinPool forkJoinPool;
  @Lazy
  private final TaskFactory taskFactory;

  public void init() {
    triggerTasks();
  }

  private void triggerTasks() {
    List<Task> tasks = new ArrayList<>();
    for (int i = 0; i < taskConfig.getTotalTasks(); i++) {
      tasks.add(taskFactory.createTask());
    }
    List<CompletableFuture<String>> futures = new ArrayList<>();
    tasks.forEach(task -> {
      futures.add(CompletableFuture.supplyAsync(task::call, forkJoinPool));
    });
    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    log.info("Finished executing {} tasks", tasks.size());
  }

}
