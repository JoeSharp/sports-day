package com.ratracejoe.sportsday.web.config;

import com.ratracejoe.sportsday.repository.jpa.entity.ActivityEntity;
import com.ratracejoe.sportsday.repository.jpa.entity.score.PointScoreSheetEntity;
import com.ratracejoe.sportsday.repository.jpa.repository.IActivityJpaRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = {IActivityJpaRepository.class})
@EntityScan(basePackageClasses = {ActivityEntity.class, PointScoreSheetEntity.class})
public class JpaAdapterConfig {}
