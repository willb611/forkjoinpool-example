package org.example.config.tasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.concurrent.Callable;

@Slf4j
@RequiredArgsConstructor
public class Task implements Callable {
  private final Duration sleepDuration;
  private final String description;

  @Override
  public String call() {
    log.info("{}From task about to sleep for {}", description, sleepDuration);
    try {
      Thread.sleep(sleepDuration.toMillis());
    } catch (InterruptedException e) {
      log.warn("{}Error while sleeping", description, e);
    }
    log.info("{}Exiting task", description);
    return description;
  }
}
