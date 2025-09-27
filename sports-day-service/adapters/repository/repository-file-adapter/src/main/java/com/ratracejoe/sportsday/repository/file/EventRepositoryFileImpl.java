package com.ratracejoe.sportsday.repository.file;

import static com.ratracejoe.sportsday.repository.file.CsvUtils.CSV_DELIMITER;

import com.ratracejoe.sportsday.domain.model.CompetitorType;
import com.ratracejoe.sportsday.domain.model.Event;
import com.ratracejoe.sportsday.domain.model.EventState;
import com.ratracejoe.sportsday.domain.model.ScoreType;
import com.ratracejoe.sportsday.ports.outgoing.repository.IEventRepository;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventRepositoryFileImpl extends GenericEntityFileRepository<Event>
    implements IEventRepository {
  public EventRepositoryFileImpl(Path rootDirectory) {
    super(Event.class, Event::id, "event", rootDirectory);
  }

  @Override
  public String toCsv(Event domain) {
    return Stream.of(
            domain.id(),
            domain.activityId(),
            domain.state(),
            domain.competitorType(),
            domain.scoreType(),
            domain.maxParticipants())
        .map(Objects::toString)
        .collect(Collectors.joining(CSV_DELIMITER));
  }

  @Override
  public Event fromCsvParts(String[] csvParts) {
    return new Event(
        UUID.fromString(csvParts[0]),
        UUID.fromString(csvParts[1]),
        EventState.valueOf(csvParts[2]),
        CompetitorType.valueOf(csvParts[3]),
        ScoreType.valueOf(csvParts[4]),
        Integer.parseInt(csvParts[5]));
  }
}
