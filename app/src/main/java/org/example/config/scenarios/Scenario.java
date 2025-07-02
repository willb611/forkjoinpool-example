package org.example.config.scenarios;

import lombok.Getter;
import org.example.config.ForkJoinProperties;
import org.example.config.TaskProperties;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

public enum Scenario {
  DEFAULT(ForkJoinProperties.DEFAULT, TaskProperties.DEFAULT),
  FAST(ForkJoinProperties.DEFAULT, TaskProperties.builder().sleepTimeUnit(ChronoUnit.MILLIS).build()),

  LIMITED(ForkJoinProperties.builder()
          .minRunnable(1)
          .corePoolSize(5)
          .maxPoolSize(10)
          .description("limited, uses all 10 threads")
          .build(),
      TaskProperties.builder()
          .totalTasks(100).build()
      ),
  REJECTION_ERRORS(ForkJoinProperties.builder()
      .minRunnable(10)
      .corePoolSize(10)
      .maxPoolSize(10)
      .description("errors for more than 10 tasks")
      .build(),
    TaskProperties.builder().totalTasks(100).build()
  ),

  ANOTHER_ERROR_CONFIG(ForkJoinProperties.builder()
      .minRunnable(1)
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
