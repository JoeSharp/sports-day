package com.ratracejoe.sportsday.jpa.model;

import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Embeddable
@Data
@AllArgsConstructor
public class MemberId {
  private UUID teamId;
  private UUID competitorId;
}
