package com.ratracejoe.sportsday.repository.jpa.entity;

import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.domain.model.CompetitorType;
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

  private CompetitorType type;
  private String name;

  public static CompetitorEntity domainToEntity(Competitor domain) {
    return new CompetitorEntity(domain.id(), domain.type(), domain.name());
  }

  public static Competitor entityToDomain(CompetitorEntity entity) {
    return new Competitor(entity.getId(), entity.getType(), entity.getName());
  }
}
