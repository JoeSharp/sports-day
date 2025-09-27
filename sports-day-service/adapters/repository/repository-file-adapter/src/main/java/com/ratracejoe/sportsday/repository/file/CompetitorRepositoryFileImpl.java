package com.ratracejoe.sportsday.repository.file;

import static com.ratracejoe.sportsday.repository.file.CsvUtils.CSV_DELIMITER;

import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.domain.model.CompetitorType;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompetitorRepositoryFileImpl extends GenericEntityFileRepository<Competitor> {
  protected CompetitorRepositoryFileImpl(Path rootDirectory) {
    super(Competitor.class, Competitor::id, "competitor", rootDirectory);
  }

  @Override
  public String toCsv(Competitor domain) {
    return Stream.of(domain.id(), domain.type(), domain.name())
        .map(Objects::toString)
        .collect(Collectors.joining(CSV_DELIMITER));
  }

  @Override
  public Competitor fromCsvParts(String[] csvParts) {
    return new Competitor(
        UUID.fromString(csvParts[0]), CompetitorType.valueOf(csvParts[1]), csvParts[2]);
  }
}
