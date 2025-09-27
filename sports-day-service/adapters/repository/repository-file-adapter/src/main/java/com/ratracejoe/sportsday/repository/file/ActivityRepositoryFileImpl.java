package com.ratracejoe.sportsday.repository.file;

import static com.ratracejoe.sportsday.repository.file.CsvUtils.CSV_DELIMITER;

import com.ratracejoe.sportsday.domain.model.Activity;
import com.ratracejoe.sportsday.ports.outgoing.repository.IActivityRepository;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ActivityRepositoryFileImpl extends GenericEntityFileRepository<Activity>
    implements IActivityRepository {
  public ActivityRepositoryFileImpl(Path rootDirectory) {
    super(Activity.class, Activity::id, "activity", rootDirectory);
  }

  @Override
  public String toCsv(Activity domain) {
    return Stream.of(domain.id(), domain.name(), domain.description())
        .map(Objects::toString)
        .collect(Collectors.joining(CSV_DELIMITER));
  }

  @Override
  public Activity fromCsvParts(String[] csvParts) {
    return new Activity(UUID.fromString(csvParts[0]), csvParts[1], csvParts[2]);
  }
}
