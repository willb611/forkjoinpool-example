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
    int parallelism = 60;
    int corePoolSize = 100;
    int maxPoolSize = 200;
    int defaultForkJoinKeepAlive = 360;
    log.info("parallelism: {}, corePoolSize: {}, maxPoolSize: {}, defaultKeepAlive: {}",
        parallelism, corePoolSize, maxPoolSize, defaultForkJoinKeepAlive);
    return new ForkJoinPool(parallelism, threadFactory, null, true, corePoolSize, maxPoolSize,
        1,null, defaultForkJoinKeepAlive, TimeUnit.MILLISECONDS);
  }
}
