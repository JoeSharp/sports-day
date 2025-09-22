package com.ratracejoe.sportsday.repository.jpa.service.score;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.domain.model.score.FinishingOrder;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class FinishingOrderRepositoryJpaIImplTest {
  @Autowired private FinishingOrderRepositoryJpaImpl finishingOrderRepository;

  @Test
  void createAndFetch() {
    // Given
    FinishingOrder finishingOrder =
        new FinishingOrder(UUID.randomUUID(), List.of(UUID.randomUUID(), UUID.randomUUID()));

    // When
    finishingOrderRepository.save(finishingOrder);
    FinishingOrder found = finishingOrderRepository.getById(finishingOrder.eventId());

    // Then
    assertThat(found).isEqualTo(finishingOrder);
  }
}
