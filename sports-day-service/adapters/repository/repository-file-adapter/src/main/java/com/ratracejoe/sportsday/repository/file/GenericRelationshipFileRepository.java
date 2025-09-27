package com.ratracejoe.sportsday.repository.file;

import static com.ratracejoe.sportsday.repository.file.CsvUtils.CSV_DELIMITER;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GenericRelationshipFileRepository extends GenericFileRepository<Relationship> {
  protected GenericRelationshipFileRepository(String entityName, Path rootDirectory) {
    super(entityName, rootDirectory);
  }

  public void relate(UUID left, UUID right) {
    List<Relationship> all = new ArrayList<>();
    all.add(new Relationship(left, right));
    all.addAll(loadAll());
    saveAll(all);
  }

  public List<UUID> getRightByLeft(UUID left) {
    return loadAll().stream().filter(r -> left.equals(r.left())).map(Relationship::right).toList();
  }

  @Override
  public String toCsv(Relationship domain) {
    return Stream.of(domain.left(), domain.right())
        .map(UUID::toString)
        .collect(Collectors.joining(CSV_DELIMITER));
  }

  @Override
  public Relationship fromCsvParts(String[] csvParts) {
    return new Relationship(UUID.fromString(csvParts[0]), UUID.fromString(csvParts[1]));
  }
}
