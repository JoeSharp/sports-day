package com.ratracejoe.sportsday.repository.jpa.service.score;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.domain.model.score.TimedFinishingOrder;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class TimedFinishingOrderRepositoryJpaIImplTest {
  @Autowired private TimedFinishingOrderRepositoryJpaImpl timedFinishingOrderRepository;

  @Test
  void createAndFetch() {
    // Given
    TimedFinishingOrder finishingOrder =
        new TimedFinishingOrder(
            UUID.randomUUID(),
            Map.of(UUID.randomUUID(), 5L, UUID.randomUUID(), 7L, UUID.randomUUID(), 3L));

    // When
    timedFinishingOrderRepository.save(finishingOrder);
    TimedFinishingOrder found = timedFinishingOrderRepository.getById(finishingOrder.eventId());

    // Then
    assertThat(found).isEqualTo(finishingOrder);
  }
}
