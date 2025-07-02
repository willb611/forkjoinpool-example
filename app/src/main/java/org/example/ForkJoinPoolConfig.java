package org.example;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.example.factories.CustomForkJoinWorkerThreadFactory;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

@UtilityClass
@Slf4j
public class ForkJoinPoolConfig {

  public ForkJoinPool createForkJoinPool() {
    CustomForkJoinWorkerThreadFactory threadFactory = new CustomForkJoinWorkerThreadFactory("customForkJoinPool");
    int parallelism = 60;
    int minPoolSize = 100;
    int maxPoolSize = 100;
    int defaultForkJoinKeepAlive = 360;
    log.info("parallelism: {}, minPoolSize: {}, maxPoolSize: {}, defaultKeepAlive: {}",
        parallelism, minPoolSize, maxPoolSize, defaultForkJoinKeepAlive);
    return new ForkJoinPool(parallelism, threadFactory, null, true, minPoolSize, maxPoolSize,
        1,null, defaultForkJoinKeepAlive, TimeUnit.MILLISECONDS);
  }
}
