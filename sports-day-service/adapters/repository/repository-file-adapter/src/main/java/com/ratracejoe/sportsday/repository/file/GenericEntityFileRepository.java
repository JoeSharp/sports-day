package com.ratracejoe.sportsday.repository.file;

import static com.ratracejoe.sportsday.repository.file.CsvUtils.CSV_DELIMITER;

import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.ports.outgoing.repository.IGenericRepository;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class GenericEntityFileRepository<T> extends GenericFileRepository<T>
    implements IGenericRepository<T> {
  private final Class<T> clazz;
  private final Function<T, UUID> idExtractor;

  protected GenericEntityFileRepository(
      Class<T> clazz, Function<T, UUID> idExtractor, String entityName, Path rootDirectory) {
    super(entityName, rootDirectory);
    this.clazz = clazz;
    this.idExtractor = idExtractor;
  }

  public T fromCsv(String csv) {
    String[] parts = csv.split(CSV_DELIMITER);
    return fromCsvParts(parts);
  }

  @Override
  public T getById(UUID id) throws NotFoundException {
    return loadAll().stream()
        .filter(t -> id.equals(idExtractor.apply(t)))
        .findAny()
        .orElseThrow(() -> new NotFoundException(clazz, id));
  }

  @Override
  public List<T> getAll() {
    return loadAll();
  }

  @Override
  public void save(T domain) {
    List<T> all =
        Stream.concat(
                Stream.of(domain),
                loadAll().stream()
                    .filter(t -> !idExtractor.apply(domain).equals(idExtractor.apply(t))))
            .toList();
    saveAll(all);
  }

  @Override
  public void deleteById(UUID id) throws NotFoundException {
    List<T> all = loadAll().stream().filter(t -> !id.equals(idExtractor.apply(t))).toList();
    saveAll(all);
  }
}
