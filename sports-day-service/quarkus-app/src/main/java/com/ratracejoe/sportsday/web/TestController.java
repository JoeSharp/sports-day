package com.ratracejoe.sportsday.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO Remove once others are working. This was added to prove quarkus-spring-web was picking these
// annotations up.
@RestController
public class TestController {

  @GetMapping("/test")
  public String test() {
    return "test";
  }
}
