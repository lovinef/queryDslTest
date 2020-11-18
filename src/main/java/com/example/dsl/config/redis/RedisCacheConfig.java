₩package com.example.dsl.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RedisCacheConfig extends CachingConfigurerSupport {
    @Autowired
    JedisConnectionFactory jedisConnectionFactory;

    // 기본 keyGenerator를 대체하며, 키값 설정이 없는 경우 자동 동작하게 된다.
    @Bean("keyGenerator")
    public KeyGenerator keyGenerator() {
        return (o, method, objects) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(o.getClass().getSimpleName());
            sb.append(".");
            sb.append(method.getName());
            sb.append("(");
            int index = 0;
            for (Object obj : objects) {
                if (index > 0) {
                    sb.append(",");
                }
                if (o.getClass().isArray()) {
                    sb.append(Arrays.deepToString((Object[]) obj));
                } else {
                    sb.append(obj.toString());
                }
                index++;
            }
            sb.append(")");
            return sb.toString();
        };
    }

    @Primary
    @Bean("cacheManager")
    public RedisCacheManager cacheManager() {
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(jedisConnectionFactory);
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .entryTtl(Duration.ofMinutes(5L));  // 기본 캐시 5분 처리

        // 캐시 value에 맞춰 아래 ttl을 적용한다.
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put("ttl1minute", configuration.entryTtl(Duration.ofMinutes(1L)));
        cacheConfigurations.put("ttl5minute", configuration.entryTtl(Duration.ofMinutes(5L)));
        cacheConfigurations.put("ttl10minute", configuration.entryTtl(Duration.ofMinutes(10L)));

        return builder.cacheDefaults(configuration).withInitialCacheConfigurations(cacheConfigurations).build();
    }
}
