package com.project.expensemanage.commone.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.FixedDurationTtlFunction;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter.TtlFunction;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
@EnableCaching
public class CacheConfig {

  @Bean
  public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
    return RedisCacheManager.builder(connectionFactory)
        .cacheDefaults(defaultConfig())
        .withInitialCacheConfigurations(customConfigMap())
        .transactionAware()
        .build();
  }

  private RedisCacheConfiguration defaultConfig() {
    return RedisCacheConfiguration.defaultCacheConfig()
        .disableCachingNullValues()
        .enableTimeToIdle()
        .entryTtl(new FixedDurationTtlFunction(Duration.ofHours(1)));
  }

  private Map<String, RedisCacheConfiguration> customConfigMap() {
    Map<String, RedisCacheConfiguration> cacheConfigMap = new HashMap<>();

    cacheConfigMap.put("CATEGORY",defaultConfig().entryTtl(TtlFunction.persistent()));

    return cacheConfigMap;
  }
}
