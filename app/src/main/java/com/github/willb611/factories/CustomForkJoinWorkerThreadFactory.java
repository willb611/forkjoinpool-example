package com.github.willb611.factories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Slf4j
public class CustomForkJoinWorkerThreadFactory implements ForkJoinPool.ForkJoinWorkerThreadFactory {
  private final String threadNamePrefix;
  private final AtomicInteger counter =  new AtomicInteger(0);

  @Override
  public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
    CustomForkJoinWorkerThread thread = new CustomForkJoinWorkerThread(pool);
    String threadName = threadNamePrefix + "-worker-" + counter.getAndIncrement();
    thread.setName(threadName);
    log.info("Created thread: {}, {}", threadName, thread);
    return thread;
  }
}
