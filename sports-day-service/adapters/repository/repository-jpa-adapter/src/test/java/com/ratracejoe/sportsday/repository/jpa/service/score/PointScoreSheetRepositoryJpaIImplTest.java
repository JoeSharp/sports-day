package com.ratracejoe.sportsday.repository.jpa.service.score;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PointScoreSheetRepositoryJpaIImplTest {
  @Autowired private PointScoreRepositoryJpaImpl pointScoreRepository;

  @Test
  void createAndFetch() {}
}
