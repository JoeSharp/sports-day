package com.ratracejoe.sportsday.repository.jpa.entity;

import com.ratracejoe.sportsday.domain.model.Competitor;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "competitor")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompetitorEntity {
  @Id private UUID id;

  private String name;

  public static CompetitorEntity domainToEntity(Competitor activity) {
    return new CompetitorEntity(activity.id(), activity.name());
  }

  public static Competitor entityToDomain(CompetitorEntity entity) {
    return new Competitor(entity.getId(), entity.getName());
  }
}
