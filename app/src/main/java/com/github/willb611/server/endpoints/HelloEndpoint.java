package com.github.willb611.server.endpoints;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.willb611.config.SleepHelper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class HelloEndpoint  {
  public static final String V1_HELLO_API_PATH = "/v1/hello";
  private final SleepHelper sleepHelper;

  @GetMapping(value = V1_HELLO_API_PATH, produces = MediaType.TEXT_PLAIN_VALUE)
  public String hello(@RequestParam(name = "description", required = false) String taskDescription) {
    long timeToSleepMillis = sleepHelper.getTimeToSleepMillis();
    log.info("Sleeping for {} milliseconds from {} for request {}", timeToSleepMillis, V1_HELLO_API_PATH, taskDescription);
    try {
      Thread.sleep(timeToSleepMillis);
    } catch (InterruptedException e) {
      log.warn("Interrupted while waiting for {}", V1_HELLO_API_PATH, e);
    }
    return "Hello " + taskDescription + "!";
  }
}
