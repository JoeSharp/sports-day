package com.ratracejoe.sportsday.repository.file.score;

import static com.ratracejoe.sportsday.repository.file.CsvUtils.*;

import com.ratracejoe.sportsday.domain.model.score.FinishingOrder;
import com.ratracejoe.sportsday.repository.file.GenericEntityFileRepository;
import java.nio.file.Path;
import java.util.UUID;

public class FinishingOrderRepositoryFileImpl extends GenericEntityFileRepository<FinishingOrder> {
  protected FinishingOrderRepositoryFileImpl(Path rootDirectory) {
    super(FinishingOrder.class, FinishingOrder::eventId, "finishing_order", rootDirectory);
  }

  @Override
  public String toCsv(FinishingOrder domain) {
    return String.join(
        CSV_DELIMITER, domain.eventId().toString(), uuidsToCsvPart(domain.finishers()));
  }

  @Override
  public FinishingOrder fromCsvParts(String[] csvParts) {
    return new FinishingOrder(UUID.fromString(csvParts[0]), uuidsFromCsvPart(csvParts[1]));
  }
}
