package com.github.willb611.config;

import lombok.Builder;
import lombok.Data;

/**
 * Config for {@link java.util.concurrent.ForkJoinPool}
 */
@Builder
@Data
public class ForkJoinProperties {
  @Builder.Default
  String description = "default";

  /**
   * minimumRunnable the minimum allowed number of core
   *      * threads not blocked by a join or {@link java.util.concurrent.ForkJoinPool.ManagedBlocker}.
   *      * To ensure progress, when too few unblocked threads exist and
   *      * unexecuted tasks may exist, new threads are constructed, up to
   *      * the given maximumPoolSize.  For the default value, use {@code
   *      * 1}, that ensures liveness.
   */
  // setting minRunnable too close to maxPoolSize will result in errors for lots of tasks
  // java.util.concurrent.RejectedExecutionException: Thread limit exceeded replacing blocked worker
  @Builder.Default
  int minimumRunnable = 1;
  /**
   * the number of threads to keep in the pool (unless timed out after an elapsed keep-alive). Normally (and by default) this is the same value as the parallelism level, but may be set to a larger value to reduce dynamic overhead if tasks regularly block. Using a smaller value (for example 0) has the same effect as the default.
   */
  @Builder.Default
  int corePoolSize = 1;
  @Builder.Default
  int maxPoolSize = 100;
  // targetParallelism must not be greater than maxPoolSize.
  // With blocking IO calls not using ManagedBlocker interface, this is the only one that matters really.
  @Builder.Default
  int targetParallelism = 10;
  @Builder.Default
  int keepAliveMillis = DEFAULT_KEEP_ALIVE_MILLIS;

  public static final int DEFAULT_KEEP_ALIVE_MILLIS = 3600;

  public static ForkJoinProperties DEFAULT = ForkJoinProperties.builder().build();
}
