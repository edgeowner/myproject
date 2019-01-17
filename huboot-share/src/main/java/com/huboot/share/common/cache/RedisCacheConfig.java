package com.huboot.share.common.cache;


import com.huboot.commons.utils.JsonUtil;
import com.huboot.share.common.constant.RedisHashName;
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
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

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
        RedisCacheWriter cacheWriter = new HashRedisCacheWriter(redisConnectionFactory);
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.builder(cacheWriter);
        builder.cacheDefaults(getRedisCacheConfigurationWithTtl(0));
        builder.withInitialCacheConfigurations(getRedisCacheConfigurationMap());
        return builder.build();
    }

    private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        //对特殊的vale进行过期时间配置
        redisCacheConfigurationMap.put(RedisHashName.USER_SERVICE_AUTH_LOGIN_CODE_ZKSHOP, this.getRedisCacheConfigurationWithTtl(10 * 60));
        redisCacheConfigurationMap.put(RedisHashName.USER_SERVICE_AUTH_LOGIN_CODE_ZKUSER, this.getRedisCacheConfigurationWithTtl(10 * 60));
        return redisCacheConfigurationMap;
    }

    private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer seconds) {
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer(JsonUtil.buildNormalMapperWithDefaultTyping().getMapper());

        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        cacheConfiguration = cacheConfiguration.disableCachingNullValues();
        cacheConfiguration = cacheConfiguration.disableKeyPrefix();
        cacheConfiguration = cacheConfiguration.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringSerializer));
        cacheConfiguration = cacheConfiguration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonRedisSerializer));
        cacheConfiguration = cacheConfiguration.entryTtl(Duration.ofSeconds(seconds));
        return cacheConfiguration;
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
                        if ("".equals(sb.toString())) {
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
