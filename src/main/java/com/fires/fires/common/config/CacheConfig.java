package com.fires.fires.common.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    //한국투자증권 access key보관 key값
    public static final String CACHE_NAME_KIS_ACCESS_KEY = "Kis:accessKey";

    /**
     * 불필요한 불필요한 access key api 호출을 막기위한 cache
     * 한국투자증권 접속 key, 1일 단위로 access key가 갱신된다.
     * @return access key를 1일 간 보관하는 cache 반환
     */
    @Bean
    public CaffeineCache kisCaffeineConfig() {
        return new CaffeineCache(CACHE_NAME_KIS_ACCESS_KEY, Caffeine.newBuilder()
                                                                    .expireAfterWrite(86400, TimeUnit.SECONDS)
                                                                    .build());
    }
}
