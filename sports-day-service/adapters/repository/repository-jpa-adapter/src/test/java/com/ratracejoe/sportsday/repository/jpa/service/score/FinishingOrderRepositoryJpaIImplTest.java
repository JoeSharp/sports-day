package com.ratracejoe.sportsday.repository.jpa.service.score;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class FinishingOrderRepositoryJpaIImplTest {
  @Autowired private FinishingOrderRepositoryJpaImpl finishingOrderRepository;

  @Test
  void createAndFetch() {}
}
