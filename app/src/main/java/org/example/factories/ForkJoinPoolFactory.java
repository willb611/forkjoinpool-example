package org.example.factories;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.example.config.ForkJoinProperties;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

@UtilityClass
@Slf4j
public class ForkJoinPoolFactory {

  public ForkJoinPool createForkJoinPool(ForkJoinProperties config) {
    CustomForkJoinWorkerThreadFactory threadFactory = new CustomForkJoinWorkerThreadFactory("customForkJoinPool");
    log.info("forkJoinConfig: {}", config);
    return new ForkJoinPool(config.getTargetParallelism(), threadFactory, null, true, config.getCorePoolSize(),
        config.getMaxPoolSize(),
        config.getMinimumRunnable(),null, config.getKeepAliveMillis(), TimeUnit.MILLISECONDS);
  }
}
