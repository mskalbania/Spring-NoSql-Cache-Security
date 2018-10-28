package edu.spring.data.nosql.redis.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableCaching
@Import(value = {DataBaseConfig.class, SecurityConfig.class})
public class ApplicationConfiguration {

    @Bean
    public CacheManager cacheManager() { //OK FOR TESTING NOT FOR PRODUCTION
        return new ConcurrentMapCacheManager();
    }

}
