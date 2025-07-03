package com.github.willb611.config.scenarios;

import lombok.Getter;
import com.github.willb611.config.ForkJoinProperties;
import com.github.willb611.config.TaskProperties;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

public enum Scenario {
  DEFAULT(ForkJoinProperties.DEFAULT, TaskProperties.DEFAULT),
  FAST(ForkJoinProperties.DEFAULT, TaskProperties.builder().sleepTimeUnit(ChronoUnit.MILLIS).build()),
  HUNDRED_TASKS_AT_ONCE(ForkJoinProperties.builder().targetParallelism(100).build(), TaskProperties.DEFAULT),
  TEN_TASKS_AT_ONCE_FOR_ONE_HUNDRED(ForkJoinProperties.builder().targetParallelism(10).build(), TaskProperties.builder().totalTasks(100).build()),

  WITH_MANAGED_BLOCKING_RESULTS_SOME_ERRORS(ForkJoinProperties.builder().targetParallelism(10).maxPoolSize(10).build(),
      TaskProperties.builder().totalTasks(50).taskIntegratesWithFJPBlockingMode(true).build()),

  LIMITED(ForkJoinProperties.builder()
          .minimumRunnable(1)
          .corePoolSize(5)
          .maxPoolSize(10)
          .description("limited, uses all 10 threads")
          .build(),
      TaskProperties.builder()
          .totalTasks(100).build()
      ),
  REJECTION_ERRORS(ForkJoinProperties.builder()
      .minimumRunnable(10)
      .corePoolSize(10)
      .maxPoolSize(10)
      .description("errors for more than 10 tasks")
      .build(),
    TaskProperties.builder().totalTasks(100).build()
  ),

  ANOTHER_ERROR_CONFIG(ForkJoinProperties.builder()
      .minimumRunnable(1)
      .corePoolSize(5)
      .maxPoolSize(10)
      .targetParallelism(5)
      .description("anotherErrorConfig")
      .build(),
      TaskProperties.builder().totalTasks(100).build()
  );

  @Getter
  private final ForkJoinProperties forkJoinProperties;
  @Getter
  private final TaskProperties taskProperties;

  private Scenario(ForkJoinProperties forkJoinProperties, TaskProperties taskProperties) {
    this.forkJoinProperties = forkJoinProperties;
    this.taskProperties = taskProperties;
  }

  public static List<Scenario> scenarioList() {
    return Arrays.asList(values());
  }
}
