package com.ratracejoe.sportsday.repository.jpa;

import com.ratracejoe.sportsday.repository.jpa.repository.IActivityJpaRepository;
import com.ratracejoe.sportsday.repository.jpa.repository.ICompetitorJpaRepository;
import com.ratracejoe.sportsday.repository.jpa.repository.IEventJpaRepository;
import com.ratracejoe.sportsday.repository.jpa.repository.ITeamJpaRepository;
import com.ratracejoe.sportsday.repository.jpa.repository.score.IFinishingOrderJpaRepository;
import com.ratracejoe.sportsday.repository.jpa.repository.score.IPointScoreSheetJpaRepository;
import com.ratracejoe.sportsday.repository.jpa.repository.score.ITimedFinishingOrderJpaRepository;
import com.ratracejoe.sportsday.repository.jpa.service.*;
import com.ratracejoe.sportsday.repository.jpa.service.score.FinishingOrderRepositoryJpaImpl;
import com.ratracejoe.sportsday.repository.jpa.service.score.PointScoreRepositoryJpaImpl;
import com.ratracejoe.sportsday.repository.jpa.service.score.TimedFinishingOrderRepositoryJpaImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class JpaTestApplication {
  @Bean
  public ActivityRepositoryJpaImpl activityRepositoryJpa(IActivityJpaRepository jpaRepository) {
    return new ActivityRepositoryJpaImpl(jpaRepository);
  }

  @Bean
  public CompetitorRepositoryJpaImpl competitorRepository(ICompetitorJpaRepository jpaRepository) {
    return new CompetitorRepositoryJpaImpl(jpaRepository);
  }

  @Bean
  public EventRepositoryJpaImpl eventRepository(IEventJpaRepository jpaRepository) {
    return new EventRepositoryJpaImpl(jpaRepository);
  }

  @Bean
  public TeamRepositoryJpaImpl teamRepository(ITeamJpaRepository jpaRepository) {
    return new TeamRepositoryJpaImpl(jpaRepository);
  }

  @Bean
  public ParticipantRepositoryJpaImpl participantRepository(JdbcTemplate jdbcTemplate) {
    return new ParticipantRepositoryJpaImpl(jdbcTemplate);
  }

  @Bean
  public MembershipRepositoryJpaImpl membershipRepository(JdbcTemplate jdbcTemplate) {
    return new MembershipRepositoryJpaImpl(jdbcTemplate);
  }

  @Bean
  public FinishingOrderRepositoryJpaImpl finishingOrderRepository(
      IFinishingOrderJpaRepository jpaRepository) {
    return new FinishingOrderRepositoryJpaImpl(jpaRepository);
  }

  @Bean
  public TimedFinishingOrderRepositoryJpaImpl timedFinishingOrderRepository(
      ITimedFinishingOrderJpaRepository jpaRepository) {
    return new TimedFinishingOrderRepositoryJpaImpl(jpaRepository);
  }

  @Bean
  public PointScoreRepositoryJpaImpl pointScoreRepository(
      IPointScoreSheetJpaRepository jpaRepository) {
    return new PointScoreRepositoryJpaImpl(jpaRepository);
  }
}
