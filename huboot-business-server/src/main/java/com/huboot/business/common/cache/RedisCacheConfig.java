package com.huboot.business.common.cache;


import com.huboot.business.common.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/7/6 0006.
 */
@Configuration
@EnableCaching
@ComponentScan(basePackages = "com.huboot")
@ConditionalOnClass(RedisConnectionFactory.class)
public class RedisCacheConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;


    @Bean
    @Primary
    public CacheManager redisCacheManager() {
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer(JsonUtil.buildNormalMapperWithDefaultTyping().getMapper());

        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        cacheConfiguration = cacheConfiguration.disableCachingNullValues();
        cacheConfiguration = cacheConfiguration.disableKeyPrefix();
        cacheConfiguration = cacheConfiguration.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringSerializer));
        cacheConfiguration = cacheConfiguration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonRedisSerializer));

        RedisCacheWriter cacheWriter = new HashRedisCacheWriter(redisConnectionFactory);

        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.builder(cacheWriter);
        builder.cacheDefaults(cacheConfiguration);
        return builder.build();
    }

    @Bean
    @Primary
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                if (objects.length == 0) {
                    return "-";
                } else {
                    StringBuilder sb = new StringBuilder("");
                    for (Object obj : objects) {
                        if("".equals(sb.toString())) {
                            sb.append(obj.toString());
                        } else {
                            sb.append("-" + obj.toString());
                        }
                    }
                    return sb.toString();
                }
            }
        };
    }
}
