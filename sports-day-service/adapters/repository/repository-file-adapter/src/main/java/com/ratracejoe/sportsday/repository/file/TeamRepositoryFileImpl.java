package com.ratracejoe.sportsday.repository.file;

import static com.ratracejoe.sportsday.repository.file.CsvUtils.CSV_DELIMITER;

import com.ratracejoe.sportsday.domain.model.Team;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TeamRepositoryFileImpl extends GenericEntityFileRepository<Team> {
  protected TeamRepositoryFileImpl(Path rootDirectory) {
    super(Team.class, Team::id, "team", rootDirectory);
  }

  @Override
  public String toCsv(Team domain) {
    return Stream.of(domain.id(), domain.name())
        .map(Objects::toString)
        .collect(Collectors.joining(CSV_DELIMITER));
  }

  @Override
  public Team fromCsvParts(String[] csvParts) {
    return new Team(UUID.fromString(csvParts[0]), csvParts[1]);
  }
}
