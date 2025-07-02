package org.example.config.tasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.client.HelloClient;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Callable;

@Slf4j
@RequiredArgsConstructor
public class Task implements Callable<String> {
  private final HelloClient helloClient;
  private final Duration sleepDuration;
  private final String taskDescription;

  @Override
  public String call() {
    info("From task about to sleep for {} from thread {}", '?', Thread.currentThread().getName());
    try {
//      Thread.sleep(sleepDuration.toMillis());
      long start = System.nanoTime();
      String response = helloClient.sayHello(taskDescription);
      long end = System.nanoTime();
      long timeTakenNanos = end - start;
      Duration duration = Duration.of(timeTakenNanos, ChronoUnit.NANOS);
      info("Took {} and got {}", duration, response);
    } catch (Exception e) {
      warn("Error while sleeping", e);
    }
    info("Exiting task", taskDescription);
    return taskDescription;
  }

  private void info(String message, Object... args) {
    log.info(String.format("[%s] %s", taskDescription, message), args);
  }

  private void warn(String message, Object... args) {
    log.warn(String.format("[%s] %s", taskDescription, message), args);
  }
}
