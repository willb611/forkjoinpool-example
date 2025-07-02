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
  public ForkJoinPool forkJoinPool(ForkJoinConfig forkJoinConfig) {
    log.info("Creating ForkJoinPool for forkJoinConfig: {}", forkJoinConfig);
    return ForkJoinPoolFactory.createForkJoinPool(forkJoinConfig);
  }

  @Bean
  public TaskFactory taskFactory(HelloClient helloClient) {
    return new TaskFactory(helloClient);
  }
}
