package com.github.willb611.config.scenarios;

import joptsimple.internal.Strings;
import lombok.extern.slf4j.Slf4j;
import com.github.willb611.config.ForkJoinProperties;
import com.github.willb611.config.TaskProperties;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Configuration
@Slf4j
public class ScenarioProvider {
  public static final String ARGUMENT_NAME_SCENARIO = "scenario";

  @Bean
  public TaskProperties taskConfig(ApplicationArguments args) {
    return getScenarioFor(args).getTaskProperties();
  }

  @Bean
  public ForkJoinProperties forkJoinConfig(ApplicationArguments args) {
    return getScenarioFor(args).getForkJoinProperties();
  }

  public static Scenario getScenarioFor(ApplicationArguments arguments) {
    Scenario result = nullableListToStream(arguments.getOptionValues(ARGUMENT_NAME_SCENARIO))
        .map(Scenario::forName)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .findFirst()
        .orElseGet(() -> {
          printExampleUsage();
          return Scenario.DEFAULT;
        });
    log.info("For arguments {} chosen scenario: {}", arguments.getSourceArgs(), result);
    return result;
  }

  private static Stream<String> nullableListToStream(List<String> nullableList) {
    return Optional.ofNullable(nullableList)
        .stream()
        .flatMap(Collection::stream);
  }

  private static void printExampleUsage() {
    log.info("To provide argument, pass like '--{}=$SCENARIO_NAME' where $SCENARIO_NAME is one from: {}",
        ARGUMENT_NAME_SCENARIO, Strings.join(Scenario.scenarioNames(), ","));
  }
}
