package com.ratracejoe.sportsday.repository.jpa.service.score;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.domain.model.score.PointScoreSheet;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PointScoreSheetRepositoryJpaIImplTest {
  @Autowired private PointScoreRepositoryJpaImpl pointScoreRepository;

  @Test
  void createAndFetch() {
    // Given
    PointScoreSheet scoreSheet =
        new PointScoreSheet(
            UUID.randomUUID(),
            Map.of(UUID.randomUUID(), 5, UUID.randomUUID(), 7, UUID.randomUUID(), 3));

    // When
    pointScoreRepository.save(scoreSheet);
    PointScoreSheet found = pointScoreRepository.getById(scoreSheet.eventId());

    // Then
    assertThat(found).isEqualTo(scoreSheet);
  }
}
