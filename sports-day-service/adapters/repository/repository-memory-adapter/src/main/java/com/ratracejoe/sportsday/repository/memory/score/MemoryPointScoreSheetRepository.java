package com.ratracejoe.sportsday.repository.memory.score;

import com.ratracejoe.sportsday.domain.model.score.PointScoreSheet;
import com.ratracejoe.sportsday.ports.outgoing.repository.score.IPointScoreSheetRepository;
import com.ratracejoe.sportsday.repository.memory.MemoryGenericRepository;

public class MemoryPointScoreSheetRepository extends MemoryGenericRepository<PointScoreSheet>
    implements IPointScoreSheetRepository {
  public MemoryPointScoreSheetRepository() {
    super(PointScoreSheet.class, PointScoreSheet::eventId);
  }
}
