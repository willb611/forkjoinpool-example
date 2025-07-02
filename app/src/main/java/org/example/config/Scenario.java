package org.example.config;

import lombok.Getter;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

public enum Scenario {
  DEFAULT(ForkJoinConfig.DEFAULT, TaskConfig.DEFAULT),
  FAST(ForkJoinConfig.DEFAULT, TaskConfig.builder().sleepTimeUnit(ChronoUnit.MILLIS).build()),

  LIMITED(ForkJoinConfig.builder()
          .minRunnable(1)
          .corePoolSize(5)
          .maxPoolSize(10)
          .description("limited, uses all 10 threads")
          .build(),
      TaskConfig.builder()
          .totalTasks(100).build()
      ),
  REJECTION_ERRORS(ForkJoinConfig.builder()
      .minRunnable(10)
      .corePoolSize(10)
      .maxPoolSize(10)
      .description("errors for more than 10 tasks")
      .build(),
    TaskConfig.builder().totalTasks(100).build()
  ),

  ANOTHER_ERROR_CONFIG(ForkJoinConfig.builder()
      .minRunnable(1)
      .corePoolSize(5)
      .maxPoolSize(10)
      .targetParallelism(5)
      .description("anotherErrorConfig")
      .build(),
      TaskConfig.builder().totalTasks(100).build()
  );

  @Getter
  private final ForkJoinConfig forkJoinConfig;
  @Getter
  private final TaskConfig taskConfig;

  private Scenario(ForkJoinConfig forkJoinConfig, TaskConfig taskConfig) {
    this.forkJoinConfig = forkJoinConfig;
    this.taskConfig = taskConfig;
  }

  public static List<Scenario> scenarioList() {
    return Arrays.asList(values());
  }
}
