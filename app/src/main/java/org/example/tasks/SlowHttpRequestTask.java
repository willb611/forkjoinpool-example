package org.example.tasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.client.HelloClient;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@RequiredArgsConstructor
public class SlowHttpRequestTask implements Callable<String>, ForkJoinPool.ManagedBlocker {
  private final HelloClient helloClient;
  private final String taskDescription;
  private volatile boolean finished = false;
  private final ReentrantLock lock = new ReentrantLock();
  private final Semaphore semaphore = new Semaphore(1);
  private final boolean requiresBlockingIO;
  private AtomicReference<String> apiResponse = null;

  @Override
  public String call() {
    info("Entry to task from thread {}",  Thread.currentThread().getName());
    try {
      long start = System.nanoTime();
      ForkJoinPool.managedBlock(this);
      long end = System.nanoTime();
      long timeTakenNanos = end - start;
      Duration duration = Duration.of(timeTakenNanos, ChronoUnit.NANOS);
      info(" managedBlock completed took {} and got {}", duration, "n/a");
      finished = true;
    } catch (Exception e) {
      warn("Error while executing fetch", e);
    }
    info("Exiting task", taskDescription);
    return taskDescription;
  }

  private String logMessageWithPrefix(String message) {
    return String.format("[%s-requiresBlockingIO=%s] %s", taskDescription, requiresBlockingIO, message);
  }

  private void info(String message, Object... args) {
    log.info(logMessageWithPrefix(message), args);
  }

  private void warn(String message, Object... args) {
    log.warn(logMessageWithPrefix(message), args);
  }

  /**
   * Possibly blocks the current thread, for example waiting for a lock or condition.
   * @throws InterruptedException – if interrupted while waiting (the method is not required to do so, but is allowed to)
   * @return true if no additional blocking is necessary (i.e., if isReleasable would return true)
   */
  @Override
  public boolean block() throws InterruptedException {
    if (requiresBlockingIO) {
      info("{} is true, so making api call", requiresBlockingIO);
      makeApiCall();
    } else {
      apiResponse = new AtomicReference<>("n/a");
    }
    return true;
  }

  private void makeApiCall() {
    apiResponse = new AtomicReference<>(helloClient.sayHello(taskDescription));
  }

  @Override
  public boolean isReleasable() {
    boolean result = finished || !requiresBlockingIO;
    info("returning result {} from isReleasable method", result);
    return result;
  }
}
