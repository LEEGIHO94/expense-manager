package com.project.expensemanage.commone.config;

import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer;

import com.project.expensemanage.commone.config.cache.CacheKey;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.FixedDurationTtlFunction;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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
        .serializeKeysWith(fromSerializer(new StringRedisSerializer()))
        .serializeValuesWith(fromSerializer(new Jackson2JsonRedisSerializer<>(Object.class)))
        .disableCachingNullValues()
        .enableTimeToIdle()
        .entryTtl(new FixedDurationTtlFunction(Duration.ofHours(1)));
  }

  private Map<String, RedisCacheConfiguration> customConfigMap() {
    Map<String, RedisCacheConfiguration> cacheConfigMap = new HashMap<>();
    setCacheToMap(cacheConfigMap);
    return cacheConfigMap;
  }

  private void setCacheToMap(Map<String,RedisCacheConfiguration> customConfigMap){
    for (CacheKey key : CacheKey.values()) {
      customConfigMap.put(key.getKey(),defaultConfig().entryTtl(key));
    }
  }
}
