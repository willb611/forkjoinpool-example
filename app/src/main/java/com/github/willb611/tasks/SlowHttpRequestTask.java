package com.github.willb611.tasks;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import com.github.willb611.client.HelloClient;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Builder
@ToString(exclude = "helloClient")
@RequiredArgsConstructor
public class SlowHttpRequestTask implements Callable<String>, ForkJoinPool.ManagedBlocker {
  private final HelloClient helloClient;
  private final String taskDescription;
  private final boolean requiresBlockingIO;
  private final boolean integratesWithFJPManagedBlock;

  private final AtomicBoolean finished = new AtomicBoolean(false);
  private final AtomicBoolean didFail = new AtomicBoolean(false);
  private final AtomicReference<String> apiResponse = new AtomicReference<>(null);

  @Override
  public String call() {
    info("Entry to task from thread {}",  Thread.currentThread().getName());
    try {
      long start = System.nanoTime();
      if (integratesWithFJPManagedBlock) {
        ForkJoinPool.managedBlock(this);
      } else {
        makeApiCall();
      }
      long end = System.nanoTime();
      long timeTakenNanos = end - start;
      Duration duration = Duration.of(timeTakenNanos, ChronoUnit.NANOS);
      info("completed task, took {} and got {}", duration, apiResponse);
      finished.set(true);
    } catch (Exception e) {
      warn("Error while executing fetch", e);
      didFail.set(true);
    }
    info("Exiting task", taskDescription);
    return taskDescription;
  }

  private String logMessageWithPrefix(String message) {
    return String.format("[%s-blockingIO=%s] %s", taskDescription, requiresBlockingIO, message);
  }

  private void debug(String message, Object... args) {
    log.debug(logMessageWithPrefix(message), args);
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
   * @return true if no additional blocking is necessary (i.e., if isReleasable would return {@code true})
   */
  @Override
  public boolean block() throws InterruptedException {
    if (requiresBlockingIO) {
      debug("{} is true, so making api call", requiresBlockingIO);
      makeApiCall();
    } else {
      apiResponse.set("n/a");
    }
    return true;
  }

  private void makeApiCall() {
    apiResponse.set(helloClient.sayHello(taskDescription));
  }

  /**
   * Returns {@code true} if blocking is unnecessary
   */
  @Override
  public boolean isReleasable() {
    boolean result = finished.get() || !requiresBlockingIO;
    debug("returning result {} from isReleasable method", result);
    return result;
  }

  public boolean isFailed() {
    return didFail.get();
  }
}
