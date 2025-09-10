package com.ratracejoe.sportsday.repository.jpa.entity;

import com.ratracejoe.sportsday.domain.model.Activity;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "activity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityEntity {
  @Id private UUID id;

  private String name;
  private String description;

  public static ActivityEntity domainToEntity(Activity activity) {
    return new ActivityEntity(activity.id(), activity.name(), activity.description());
  }

  public static Activity entityToDomain(ActivityEntity entity) {
    return new Activity(entity.getId(), entity.getName(), entity.getDescription());
  }
}
