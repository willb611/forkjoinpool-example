package org.example;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.example.config.TaskConfig;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class JettyConfig {
  private final TaskConfig taskConfig;
  private Server server;

  @PostConstruct
  public void configure() {
    if (taskConfig.getServerType() == TaskConfig.ServerType.JETTY) {
//      int port = 8080;
//      this.server = new Server(port);
//      server.start();
//      log.info("Created jetty {}", server);
////    wireMockServer.start();
//      stubHelloEndpoint(server);
    } else {
      log.info("Server type is {}",  taskConfig.getServerType());
    }
  }

  private void stubHelloEndpoint(Server server) {
    Duration meanTimeout = Duration.of(taskConfig.getMeanSleepPerTask(), taskConfig.getSleepTimeUnit());
    double timeoutInMillis = meanTimeout.toMillis();
    double sigma = 2.5;

  }
}

