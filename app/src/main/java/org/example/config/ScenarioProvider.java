package org.example.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ScenarioProvider {
  @Bean
  public TaskConfig taskConfig(ApplicationArguments args) {
    return getScenarioFor(args).getTaskConfig();
  }

  @Bean
  public ForkJoinConfig forkJoinConfig(ApplicationArguments args) {
    return getScenarioFor(args).getForkJoinConfig();
  }

  public static Scenario getScenarioFor(ApplicationArguments arguments) {
    Scenario result = arguments.getOptionNames().stream()
        .flatMap(name -> Scenario.scenarioList().stream().filter(scenario -> scenario.name().equals(name)))
        .findFirst()
        .orElse(Scenario.DEFAULT);
    log.info("For arguments {} chosen scenario: {}", arguments.getOptionNames(), result);
    return result;
  }
}
