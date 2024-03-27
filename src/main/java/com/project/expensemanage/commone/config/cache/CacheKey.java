package com.project.expensemanage.commone.config.cache;

import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.cache.RedisCacheWriter.TtlFunction;

@Getter
@AllArgsConstructor
public enum CacheKey implements TtlFunction {
  CATEGORY("category", Duration.ZERO),
  BUDGET("budget", Duration.ofHours(1L));

  private String key;
  private Duration duration;

  @Override
  public Duration getTimeToLive(Object key, Object value) {
    return this.duration;
  }
}
