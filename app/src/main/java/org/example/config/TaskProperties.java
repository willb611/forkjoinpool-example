package org.example.config;

import lombok.Builder;
import lombok.Data;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

@Data
@Builder
public class TaskProperties {
  public static final TaskProperties DEFAULT = TaskProperties.builder().build();

  @Builder.Default
  int minSleepPerTask = 30;
  @Builder.Default
  int maxSleepPerTask = 100;
  @Builder.Default
  TemporalUnit sleepTimeUnit = ChronoUnit.SECONDS;

  @Builder.Default
  int totalTasks = 1000;

  public int getVariableSleepPerTask() {
    return maxSleepPerTask - minSleepPerTask;
  }

  public int getMeanSleepPerTask() {
    return (int)((minSleepPerTask + maxSleepPerTask) / 2.0);
  }
}
