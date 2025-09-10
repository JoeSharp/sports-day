package com.ratracejoe.sportsday.repository.jpa.service;

import com.ratracejoe.sportsday.ports.outgoing.repository.IMembershipRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

@RequiredArgsConstructor
public class MembershipRepositoryJpaImpl implements IMembershipRepository {
  private final JdbcTemplate jdbcTemplate;
  private static final Logger LOGGER = LoggerFactory.getLogger(MembershipRepositoryJpaImpl.class);

  @Override
  public void addMembership(UUID teamId, UUID competitorId) {
    int rows =
        jdbcTemplate.update(
            "INSERT INTO membership(team_id, competitor_id) VALUES (?, ?)", teamId, competitorId);
    if (rows != 1) {
      LOGGER.warn("Strange number of rows updated with membership insert {}", rows);
    }
  }

  @Override
  public List<UUID> getMemberIds(UUID teamId) {
    return jdbcTemplate.query(
        "SELECT m.competitorId AS id FROM membership m WHERE m.teamId = ?",
        ps -> ps.setString(1, teamId.toString()),
        (rs, rowNum) -> UUID.fromString(rs.getString("id")));
  }
}
