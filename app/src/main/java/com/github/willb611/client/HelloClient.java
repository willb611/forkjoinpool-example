package com.github.willb611.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "localhost", url = "localhost:8080")
public interface HelloClient {
  @RequestMapping(method = RequestMethod.GET, value = "/v1/hello")
  String sayHello(@RequestParam("description") String description);
}
