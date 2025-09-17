package com.ratracejoe.sportsday.repository.redis.entity;

import com.ratracejoe.sportsday.domain.model.CompetitorType;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("competitors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CachedCompetitor {
  @Id UUID id;
  CompetitorType type;
  @Indexed String name;
}
