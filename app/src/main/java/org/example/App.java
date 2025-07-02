package org.example;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.example.client.HelloClient;
import org.example.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

@SpringBootApplication
@EnableFeignClients(basePackageClasses =  HelloClient.class)
@Import(AppConfig.class)
@Slf4j
public class App {
    @Lazy @Setter
    private TaskScheduler taskScheduler;
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(App.class, args);
        TaskScheduler taskScheduler = applicationContext.getBean(TaskScheduler.class);
        taskScheduler.init();
        WiremockConfig wiremockConfig = applicationContext.getBean(WiremockConfig.class);
        wiremockConfig.logRequests();
    }

    public App() {
        log.info("Hello from constructor");
    }
}
