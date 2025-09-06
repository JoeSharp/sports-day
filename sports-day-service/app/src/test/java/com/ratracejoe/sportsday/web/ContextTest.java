package com.ratracejoe.sportsday.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest()
@TestPropertySource(
    properties = {
      "spring.datasource.url=jdbc:h2:mem:testdb",
      "spring.security.oauth2.resourceserver.jwt.issuer-uri:localhost",
      "spring.kafka.bootstrap-servers:localhost",
      "spring.data.redis.host:localhost",
      "spring.data.redis.port:5436",
    })
class ContextTest {
  @Autowired private ApplicationContext context;

  @Test
  void contextLoad() {
    assertThat(context).isNotNull();
  }
}
