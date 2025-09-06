package com.ratracejoe.sportsday.jpa.model.cache;

import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("activities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CachedActivity {
  @Id UUID id;

  @Indexed String name;
  String description;
}
