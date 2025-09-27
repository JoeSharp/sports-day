package com.ratracejoe.sportsday.repository.file;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CsvUtilsTest {
  @Test
  void uuidsCsv() {
    // Given
    List<UUID> uuids = List.of(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());

    // When
    String csv = CsvUtils.uuidsToCsvPart(uuids);
    List<UUID> from = CsvUtils.uuidsFromCsvPart(csv);

    // Then
    assertThat(from).isEqualTo(uuids);
  }

  @Test
  void mapCsv() {
    // Given
    Map<UUID, Integer> map =
        Map.of(
            UUID.randomUUID(), 5,
            UUID.randomUUID(), 9,
            UUID.randomUUID(), 3);

    // When
    String csv = CsvUtils.mapToCsvPart(map);
    Map<UUID, Integer> from = CsvUtils.mapFromCsvPart(csv, Integer::parseInt);

    // Then
    assertThat(from).isEqualTo(map);
  }
}
