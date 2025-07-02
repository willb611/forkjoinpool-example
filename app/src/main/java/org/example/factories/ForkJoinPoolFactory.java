package org.example.factories;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

@UtilityClass
@Slf4j
public class ForkJoinPoolFactory {

  public ForkJoinPool createForkJoinPool() {
    CustomForkJoinWorkerThreadFactory threadFactory = new CustomForkJoinWorkerThreadFactory("customForkJoinPool");
    int minRunnable = 15;
    int corePoolSize = 1;
    int maxPoolSize = 1000;
    int parallelism = 10; // parallelism must not be greater than maxPoolSize. With blocking IO calls, this is the only one that matters really.
    int defaultForkJoinKeepAlive = 360;
    log.info("parallelism: {}, corePoolSize: {}, maxPoolSize: {}, defaultKeepAlive: {}",
        parallelism, corePoolSize, maxPoolSize, defaultForkJoinKeepAlive);
    return new ForkJoinPool(parallelism, threadFactory, null, true, corePoolSize, maxPoolSize,
        minRunnable,null, defaultForkJoinKeepAlive, TimeUnit.MILLISECONDS);
  }
}
