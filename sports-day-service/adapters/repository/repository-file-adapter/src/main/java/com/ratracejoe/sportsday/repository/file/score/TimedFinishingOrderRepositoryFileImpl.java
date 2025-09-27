package com.ratracejoe.sportsday.repository.file.score;

import static com.ratracejoe.sportsday.repository.file.CsvUtils.*;

import com.ratracejoe.sportsday.domain.model.score.TimedFinishingOrder;
import com.ratracejoe.sportsday.ports.outgoing.repository.score.ITimedFinishingOrderRepository;
import com.ratracejoe.sportsday.repository.file.GenericEntityFileRepository;
import java.nio.file.Path;
import java.util.UUID;

public class TimedFinishingOrderRepositoryFileImpl
    extends GenericEntityFileRepository<TimedFinishingOrder>
    implements ITimedFinishingOrderRepository {
  public TimedFinishingOrderRepositoryFileImpl(Path rootDirectory) {
    super(
        TimedFinishingOrder.class,
        TimedFinishingOrder::eventId,
        "timed_finishing_order",
        rootDirectory);
  }

  @Override
  public String toCsv(TimedFinishingOrder domain) {
    return String.join(
        CSV_DELIMITER, domain.eventId().toString(), mapToCsvPart(domain.finishTimeMilliseconds()));
  }

  @Override
  public TimedFinishingOrder fromCsvParts(String[] csvParts) {
    return new TimedFinishingOrder(
        UUID.fromString(csvParts[0]), mapFromCsvPart(csvParts[1], Long::parseLong));
  }
}
