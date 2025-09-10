package com.ratracejoe.sportsday.repository.jpa.entity;

import com.ratracejoe.sportsday.domain.model.Team;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "team")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamEntity {
  @Id private UUID id;

  private String name;

  public static TeamEntity domainToEntity(Team activity) {
    return new TeamEntity(activity.id(), activity.name());
  }

  public static Team entityToDomain(TeamEntity entity) {
    return new Team(entity.getId(), entity.getName());
  }
}
