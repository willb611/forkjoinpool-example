package org.example.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class SleepHelper {
  private final Random random = new Random();
  private final TaskConfig taskConfig;

  public long getTimeToSleepMillis() {
    return getTimeToSleep().toMillis();
  }

  public Duration getTimeToSleep() {
    long amount = taskConfig.getMinSleepPerTask() + random.nextInt(taskConfig.getVariableSleepPerTask());
    return Duration.of(amount, taskConfig.getSleepTimeUnit());
  }
}
