package com.ratracejoe.sportsday.domain.service.score;

import com.ratracejoe.sportsday.domain.exception.IncorrectEventTypeException;
import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.model.score.PointScoreSheet;
import com.ratracejoe.sportsday.ports.incoming.service.score.IPointScoreService;
import com.ratracejoe.sportsday.ports.outgoing.repository.ICompetitorRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.score.IPointScoreSheetRepository;
import java.util.UUID;

public class PointScoreService implements IPointScoreService {
  private final ICompetitorRepository competitorRepository;
  private final IPointScoreSheetRepository scoreSheetRepository;

  public PointScoreService(
      ICompetitorRepository competitorRepository, IPointScoreSheetRepository scoreSheetRepository) {
    this.competitorRepository = competitorRepository;
    this.scoreSheetRepository = scoreSheetRepository;
  }

  @Override
  public PointScoreSheet createNew(UUID eventId) {
    PointScoreSheet pointScoreSheet = PointScoreSheet.create(eventId);
    scoreSheetRepository.save(pointScoreSheet);
    return pointScoreSheet;
  }

  @Override
  public PointScoreSheet getPoints(UUID eventId)
      throws NotFoundException, IncorrectEventTypeException {
    return scoreSheetRepository.getById(eventId);
  }

  @Override
  public void addPoints(UUID eventId, UUID participantId, int points)
      throws IncorrectEventTypeException {
    PointScoreSheet scoreSheet = scoreSheetRepository.getById(eventId);
    competitorRepository.checkExists(participantId);
    PointScoreSheet newScoreSheet = scoreSheet.withScoreIncrement(participantId, points);
    scoreSheetRepository.save(newScoreSheet);
  }
}
