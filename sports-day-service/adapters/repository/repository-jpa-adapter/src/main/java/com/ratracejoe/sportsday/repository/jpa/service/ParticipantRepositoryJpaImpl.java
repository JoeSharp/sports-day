package com.ratracejoe.sportsday.repository.jpa.service;

import com.ratracejoe.sportsday.ports.outgoing.repository.IParticipantRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

@RequiredArgsConstructor
public class ParticipantRepositoryJpaImpl implements IParticipantRepository {
  private final JdbcTemplate jdbcTemplate;
  private static final Logger LOGGER = LoggerFactory.getLogger(ParticipantRepositoryJpaImpl.class);

  @Override
  public void addParticipant(UUID eventId, UUID participantId) {
    int rows =
        jdbcTemplate.update(
            "INSERT INTO participation(event_id, participant_id) VALUES (?, ?)",
            eventId,
            participantId);
    if (rows != 1) {
      LOGGER.warn("Strange number of rows updated with membership insert {}", rows);
    }
  }

  @Override
  public List<UUID> getParticipants(UUID eventId) {
    return jdbcTemplate.query(
        "SELECT m.participant_id AS id FROM participation m WHERE m.event_id = ?",
        ps -> ps.setObject(1, eventId),
        (rs, rowNum) -> UUID.fromString(rs.getString("id")));
  }
}
