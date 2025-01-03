package com.ratracejoe.sportsday.repository.cache;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.UUID;

@RedisHash("activities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CachedActivity {
    @Id
    UUID id;

    @Indexed
    String name;
    String description;
}
