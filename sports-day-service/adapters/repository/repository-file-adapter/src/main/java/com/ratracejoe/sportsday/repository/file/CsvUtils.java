package com.ratracejoe.sportsday.repository.file;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface CsvUtils {
  String CSV_DELIMITER = ",";
  String UUID_DELIMITER = ":";
  String ENTRY_DELIMITER = "+";

  static String uuidsToCsvPart(List<UUID> uuids) {
    return uuids.stream().map(Object::toString).collect(Collectors.joining(UUID_DELIMITER));
  }

  static List<UUID> uuidsFromCsvPart(String csvPart) {
    return Stream.of(csvPart.split(Pattern.quote(UUID_DELIMITER))).map(UUID::fromString).toList();
  }

  static <N> String mapToCsvPart(Map<UUID, N> map) {
    return map.entrySet().stream()
        .map(
            e ->
                Stream.of(e.getKey(), e.getValue())
                    .map(Object::toString)
                    .collect(Collectors.joining(ENTRY_DELIMITER)))
        .collect(Collectors.joining(UUID_DELIMITER));
  }

  static <N> Map<UUID, N> mapFromCsvPart(String csvPart, Function<String, N> fromString) {
    return Stream.of(csvPart.split(Pattern.quote(UUID_DELIMITER)))
        .map(e -> e.split(Pattern.quote(ENTRY_DELIMITER)))
        .collect(Collectors.toMap(e -> UUID.fromString(e[0]), e -> fromString.apply(e[1])));
  }
}
