package com.github.willb611.config.scenarios;

import org.junit.jupiter.api.Test;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScenarioProviderTest {

  @Test
  void getScenarioFast() {
    ApplicationArguments applicationArguments = new DefaultApplicationArguments("--scenario=FAST");

    assertEquals(Scenario.FAST, ScenarioProvider.getScenarioFor(applicationArguments));
  }

  @Test
  void getScenarioReturnsDefault() {
    ApplicationArguments applicationArguments = new DefaultApplicationArguments();

    assertEquals(Scenario.DEFAULT, ScenarioProvider.getScenarioFor(applicationArguments));
  }
}