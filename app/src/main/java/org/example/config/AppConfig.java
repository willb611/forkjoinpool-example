package org.example.config;

import lombok.extern.slf4j.Slf4j;
import org.example.client.HelloClient;
import org.example.factories.ForkJoinPoolFactory;
import org.example.factories.TaskFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ForkJoinPool;

@Configuration
@Slf4j
public class AppConfig {
  @Bean
  public TaskConfig exampleConfig() {
    return new TaskConfig();
  }

  @Bean
  public ForkJoinPool forkJoinPool() {
    log.info("Creating ForkJoinPool");
    return ForkJoinPoolFactory.createForkJoinPool();
  }

  @Bean
  public TaskFactory taskFactory(SleepHelper sleepHelper, HelloClient helloClient) {
    return new TaskFactory(sleepHelper, helloClient);
  }
}
