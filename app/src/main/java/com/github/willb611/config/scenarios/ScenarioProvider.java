package com.github.willb611.config.scenarios;

import lombok.extern.slf4j.Slf4j;
import com.github.willb611.config.ForkJoinProperties;
import com.github.willb611.config.TaskProperties;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ScenarioProvider {
  @Bean
  public TaskProperties taskConfig(ApplicationArguments args) {
    return getScenarioFor(args).getTaskProperties();
  }

  @Bean
  public ForkJoinProperties forkJoinConfig(ApplicationArguments args) {
    return getScenarioFor(args).getForkJoinProperties();
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
