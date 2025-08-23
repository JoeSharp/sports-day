package com.ratracejoe.sportsday.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
  private String keycloakUrl;

  @Value("${cors.ui-host}")
  private String uiHost;

  @Bean(name = "keycloak")
  public RestClient keycloakClient() {
    return RestClient.builder().baseUrl(keycloakUrl).build();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http.authorizeHttpRequests(
            request ->
                request
                    .requestMatchers(
                        new AntPathRequestMatcher("/auth/login"),
                        new AntPathRequestMatcher("/actuator/**"))
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .csrf(AbstractHttpConfigurer::disable)
        .cors(Customizer.withDefaults())
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
        .build();
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry
            .addMapping("/**")
            .allowedOrigins(uiHost)
            .allowedMethods(
                HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.DELETE.name());
      }
    };
  }
}
