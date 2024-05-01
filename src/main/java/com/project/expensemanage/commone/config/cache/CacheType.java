package com.project.expensemanage.commone.config.cache;

import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.cache.RedisCacheWriter.TtlFunction;

@Getter
@AllArgsConstructor
public enum CacheType{
  CATEGORY("CATEGORY", 60,1),
  BUDGET("BUDGET", 1*60*60,1);

  private final String cacheName;
  private final int expiresSecs;
  private final int entryMaxSize;
}
