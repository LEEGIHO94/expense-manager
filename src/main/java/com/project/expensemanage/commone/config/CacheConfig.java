package com.project.expensemanage.commone.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
@EnableCaching
public class CacheConfig {

  @Bean
  public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
    return RedisCacheManager.builder()
        .cacheDefaults(defaultConfig())
        .withInitialCacheConfigurations(customConfigMap())
        .build();
  }

  private RedisCacheConfiguration defaultConfig() {
    return RedisCacheConfiguration.defaultCacheConfig()
//        .serializeKeysWith(SerializationPair.fromSerializer(new StringRedisSerializer()))
//        .serializeValuesWith(
//            SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
        .entryTtl(Duration.ofMillis(1));
  }

  private Map<String, RedisCacheConfiguration> customConfigMap() {
    Map<String, RedisCacheConfiguration> cacheConfigMap = new HashMap<>();
    //cache 항목에 따른 기간 설정을 위한 Config
    return cacheConfigMap;
  }
}
