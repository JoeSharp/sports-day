package com.ratracejoe.sportsday.repository.file.score;

import static com.ratracejoe.sportsday.repository.file.CsvUtils.*;

import com.ratracejoe.sportsday.domain.model.score.PointScoreSheet;
import com.ratracejoe.sportsday.ports.outgoing.repository.score.IPointScoreSheetRepository;
import com.ratracejoe.sportsday.repository.file.GenericEntityFileRepository;
import java.nio.file.Path;
import java.util.UUID;

public class PointScoreRepositoryFileImpl extends GenericEntityFileRepository<PointScoreSheet>
    implements IPointScoreSheetRepository {
  public PointScoreRepositoryFileImpl(Path rootDirectory) {
    super(PointScoreSheet.class, PointScoreSheet::eventId, "point_scores", rootDirectory);
  }

  @Override
  public String toCsv(PointScoreSheet domain) {
    return String.join(CSV_DELIMITER, domain.eventId().toString(), mapToCsvPart(domain.scores()));
  }

  @Override
  public PointScoreSheet fromCsvParts(String[] csvParts) {
    return new PointScoreSheet(
        UUID.fromString(csvParts[0]), mapFromCsvPart(csvParts[1], Integer::parseInt));
  }
}
