package com.ratracejoe.sportsday.jpa.model;

import com.ratracejoe.sportsday.domain.model.Team;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "membership")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembershipEntity {
  @Id private UUID teamId;
    @Id private UUID competitorId;
}
