package com.huboot.gateway.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import com.huboot.commons.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by Administrator on 2018/8/1 0001.
 */
@Configuration
public class RedisConfig {

    @Autowired
    private ReactiveRedisConnectionFactory reactiveRedisConnectionFactory;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public ReactiveRedisTemplate reactiveRedisTemplate() {

        RedisSerializer stringSerializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer(JsonUtil.buildNormalMapperWithDefaultTyping().getMapper());

        RedisSerializationContext serializationContext = RedisSerializationContext
                .newSerializationContext()
                .key(stringSerializer)
                .value(jsonRedisSerializer)
                .hashKey(stringSerializer)
                .hashValue(jsonRedisSerializer)
                .build();
        ReactiveRedisTemplate reactiveRedisTemplate = new ReactiveRedisTemplate(reactiveRedisConnectionFactory, serializationContext);
        return reactiveRedisTemplate;
    }

    @Bean
    public RedisTemplate redisTemplate() {
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer(JsonUtil.buildNormalMapperWithDefaultTyping().getMapper());

        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(jsonRedisSerializer);

        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
}
