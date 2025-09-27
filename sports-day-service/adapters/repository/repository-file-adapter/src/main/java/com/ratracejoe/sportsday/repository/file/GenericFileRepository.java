package com.ratracejoe.sportsday.repository.file;

import static com.ratracejoe.sportsday.repository.file.CsvUtils.CSV_DELIMITER;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public abstract class GenericFileRepository<T> {
  protected final Path entityFile;

  public abstract String toCsv(T domain);

  public abstract T fromCsvParts(String[] csvParts);

  protected GenericFileRepository(String entityName, Path rootDirectory) {
    this.entityFile = rootDirectory.resolve(entityName + ".csv");
  }

  protected void saveAll(List<T> all) {
    List<String> csv = all.stream().map(this::toCsv).toList();
    try {
      Files.write(entityFile, csv, Charset.defaultCharset());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  protected List<T> loadAll() {
    if (!Files.isReadable(entityFile)) {
      return Collections.emptyList();
    }
    try (Stream<String> lines = Files.lines(entityFile)) {
      return lines.map(d -> d.split(CSV_DELIMITER)).map(this::fromCsvParts).toList();
    } catch (IOException e) {
      return Collections.emptyList();
    }
  }
}
