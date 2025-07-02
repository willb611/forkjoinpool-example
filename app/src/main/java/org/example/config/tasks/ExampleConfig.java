package org.example.config.tasks;

import lombok.Data;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

@Data
public class ExampleConfig {
  int minSleepPerTask = 30;
  int maxSleepPerTask = 100;
  TemporalUnit sleepTimeUnit = ChronoUnit.SECONDS;

  int totalTasks = 1000;

  public int getVariableSleepPerTask() {
    return maxSleepPerTask - minSleepPerTask;
  }
}
