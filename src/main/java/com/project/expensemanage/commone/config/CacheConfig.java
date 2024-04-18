package com.project.expensemanage.commone.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.project.expensemanage.commone.config.cache.CacheType;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

  @Bean
  public CacheManager cacheManager() {
    SimpleCacheManager cacheManager = new SimpleCacheManager();
    cacheManager.setCaches(getCacheList());
    return cacheManager;
  }

  private List<CaffeineCache> getCacheList() {
    return Arrays.stream(CacheType.values())
        .map(cacheType ->
            new CaffeineCache(
                cacheType.getCacheName(),
                Caffeine.newBuilder()
                    .recordStats()
                    .expireAfterWrite(cacheType.getExpiresSecs(), TimeUnit.SECONDS)
                    .maximumSize(cacheType.getEntryMaxSize())//캐시 항목의 수 -> 설정 값 이상의  데이터 존재 시 삭제로직 동작
                    .build())
        ).toList();
  }

}
