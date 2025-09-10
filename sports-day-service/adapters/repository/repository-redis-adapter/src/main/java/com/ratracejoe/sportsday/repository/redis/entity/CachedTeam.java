package com.ratracejoe.sportsday.repository.redis.entity;

import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("teams")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CachedTeam {
  @Id UUID id;

  @Indexed String name;
}
