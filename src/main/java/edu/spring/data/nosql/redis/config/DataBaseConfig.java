package edu.spring.data.nosql.redis.config;

import edu.spring.data.nosql.redis.model.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@ComponentScan(basePackages = "edu.spring.data.nosql.redis.repository")
public class DataBaseConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new StringRedisTemplate(redisConnectionFactory);
    }

    @Bean
    public RedisTemplate<String, Product> productRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Product> productRedisTemplate = new RedisTemplate<>();
        productRedisTemplate.setConnectionFactory(redisConnectionFactory);
        return productRedisTemplate;
    }
}
