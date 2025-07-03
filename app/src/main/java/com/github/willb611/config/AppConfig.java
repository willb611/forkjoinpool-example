package com.github.willb611.config;

import lombok.extern.slf4j.Slf4j;
import com.github.willb611.client.HelloClient;
import com.github.willb611.factories.ForkJoinPoolFactory;
import com.github.willb611.factories.TaskFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ForkJoinPool;

@Configuration
@Slf4j
public class AppConfig {
  @Bean
  public ForkJoinPool forkJoinPool(ForkJoinProperties forkJoinProperties) {
    log.info("Creating ForkJoinPool for forkJoinConfig: {}", forkJoinProperties);
    return ForkJoinPoolFactory.createForkJoinPool(forkJoinProperties);
  }

  @Bean
  public TaskFactory taskFactory(HelloClient helloClient) {
    return new TaskFactory(helloClient);
  }
}
