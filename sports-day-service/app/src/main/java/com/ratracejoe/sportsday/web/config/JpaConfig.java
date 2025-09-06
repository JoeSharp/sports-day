package com.ratracejoe.sportsday.web.config;

import com.ratracejoe.sportsday.jpa.model.entity.ActivityEntity;
import com.ratracejoe.sportsday.jpa.repository.ActivityJpaRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = ActivityJpaRepository.class)
@EntityScan(basePackageClasses = ActivityEntity.class)
public class JpaConfig {}
