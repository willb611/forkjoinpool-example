package org.example;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.DelayDistribution;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.config.TaskConfig;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class WiremockConfig {
  private final TaskConfig taskConfig;
  private WireMockServer wireMockServer;

  @PostConstruct
  public void configure() {
    if (taskConfig.getServerType() == TaskConfig.ServerType.WIREMOCK) {
      int port = 8080;
      WireMock.configureFor("localhost", port);
      wireMockServer = new WireMockServer(port);
      log.info("Created wiremock {}", wireMockServer);
      wireMockServer.start();
      stubHelloEndpoint(wireMockServer);
    } else {
      log.warn("Not a wiremock server");
    }
  }

  private void stubHelloEndpoint(WireMockServer wireMockServer) {
    Duration meanTimeout = Duration.of(taskConfig.getMeanSleepPerTask(), taskConfig.getSleepTimeUnit());
    double timeoutInMillis = meanTimeout.toMillis();
    double sigma = 2.5;
    log.info("Stubbing /hello to return response");
    wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/hello"))
        .willReturn(WireMock.aResponse()
            .withStatus(200)
            .withLogNormalRandomDelay(timeoutInMillis, sigma)
            .withBody("Hello")));
  }

  public void logRequests() {
    if (wireMockServer != null) {
      wireMockServer.findAllUnmatchedRequests().forEach(request -> {log.info("unmatched request {}", request);});
    }
  }
}
