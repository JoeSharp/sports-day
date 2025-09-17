package com.ratracejoe.sportsday.domain.model.score;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class PointScoreSheetTest {

  @Test
  void withScoreIncrement() {
    // Given
    UUID eventId = UUID.randomUUID();
    UUID competitorA = UUID.randomUUID();
    UUID competitorB = UUID.randomUUID();
    UUID competitorC = UUID.randomUUID();
    PointScoreSheet sheet = PointScoreSheet.create(eventId);

    // When
    PointScoreSheet sheet1 = sheet.withScoreIncrement(competitorA, 2);
    PointScoreSheet sheet2 = sheet1.withScoreIncrement(competitorB, 3);
    PointScoreSheet sheet3 = sheet2.withScoreIncrement(competitorA, 6);
    PointScoreSheet sheet4 = sheet3.withScoreIncrement(competitorC, 1);
    PointScoreSheet sheet5 = sheet4.withScoreIncrement(competitorC, 2);
    PointScoreSheet sheet6 = sheet5.withScoreIncrement(competitorB, 9);

    // Then
    assertThat(sheet6.scores())
        .containsEntry(competitorA, 8)
        .containsEntry(competitorB, 12)
        .containsEntry(competitorC, 3);
  }
}
