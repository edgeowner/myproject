package com.huboot.business.common.config;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.huboot.business.common.support.cache.B2cWeiXinEventHandle;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching
public class CacheConfig {

    // model local,cluster,sentinel
    private static final String REDIS_MODEL_LOCAL = "local";

    private static final String REDIS_MODEL_CLUSTER = "cluster";

    private static final String REDIS_MODEL_SENTINEL = "sentinel";

    @Autowired
    @Qualifier("objectMapper")
    private ObjectMapper objectMapper;

    @Value("${spring.redis.model:local}")
    private String redisModel;

    @Value("${spring.redis.host}")
    private String hosts;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.database:1}")
    private Integer index;

    public static final String REDIS_SUBSCRIBE_CHANNEL = "b2c_weixin_channel";

    private String masterNode;

    @Bean(name = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(JedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        template.setDefaultSerializer(jackson2JsonRedisSerializer);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    public <String, V> RedisTemplate<String, V> getJacksonStringTemplate(Class<V> clazz,JedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, V> redisTemplate = new RedisTemplate<String, V>();

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<V> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(clazz);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        redisTemplate.setConnectionFactory(redisConnectionFactory);

        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);

        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.setDefaultSerializer(stringRedisSerializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public JedisConnectionFactory connectionFactory() {
        if (REDIS_MODEL_LOCAL.equals(redisModel)) {
            return localModel();
        }
        if (REDIS_MODEL_CLUSTER.equals(redisModel)) {
            return clusterModel();
        }
        if (REDIS_MODEL_SENTINEL.equals(redisModel)) {
            return sentinelModel();
        }
        throw new RuntimeException("不能初始化Redis,请指定Redis的启动模式");
    }

    /**
     * localModel
     **/
    public JedisConnectionFactory localModel() {
        JedisConnectionFactory jedis = new JedisConnectionFactory();
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        String[] host = hosts.split(":");
        jedis.setHostName(host[0]);
        jedis.setPort(new Integer(host[1]));
        if (!StringUtil.isNullOrEmpty(password)) {
            jedis.setPassword(password);
        }
        if (index != 0) {
            jedis.setDatabase(index);
        }
        JedisPoolConfig poolCofig = new JedisPoolConfig();
        //TODO 这里可以设置各种属性
        jedis.setPoolConfig(poolCofig);
        jedis.afterPropertiesSet();
        return jedis;
    }

    /**
     * clusterModel
     **/
    public JedisConnectionFactory clusterModel() {
        // pool config
        JedisPoolConfig poolCofig = new JedisPoolConfig();
        // cluster config
        RedisClusterConfiguration redisClusterConfig = new RedisClusterConfiguration();
        String[] item = hosts.split("&");
        List<RedisNode> nodes = new ArrayList<RedisNode>();
        for (int i = 0; i < item.length; i++) {
            String[] host = item[i].split(":");
            RedisNode node = new RedisNode(host[0], new Integer(host[1]));
            nodes.add(node);
        }
        redisClusterConfig.setMaxRedirects(item.length);
        redisClusterConfig.setClusterNodes(nodes);
        // factory config
        JedisConnectionFactory factory = new JedisConnectionFactory(redisClusterConfig, poolCofig);
        if (!StringUtil.isNullOrEmpty(password)) {
            factory.setPassword(password);
        }
        if (index != 0) {
            factory.setDatabase(index);
        }
        factory.afterPropertiesSet();
        return factory;
    }

    /**
     * sentinelModel
     **/
    public JedisConnectionFactory sentinelModel() {
        // pool config
        JedisPoolConfig poolCofig = new JedisPoolConfig();
        // sentinel config
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
        String[] item = hosts.split("&");
        List<RedisNode> nodes = new ArrayList<RedisNode>();
        for (int i = 0; i < item.length; i++) {
            String[] host = item[i].split(":");
            RedisNode node = new RedisNode(host[0], new Integer(host[1]));
            nodes.add(node);
        }
        redisSentinelConfiguration.setMaster(masterNode);
        redisSentinelConfiguration.setSentinels(nodes);
        // factory config
        JedisConnectionFactory factory = new JedisConnectionFactory(redisSentinelConfiguration, poolCofig);
        if (!StringUtil.isNullOrEmpty(password)) {
            factory.setPassword(password);
        }
        if (index != 0) {
            factory.setDatabase(index);
        }
        factory.afterPropertiesSet();
        return factory;
    }

    /**redis 发布订阅**/
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //订阅了一个通道
        container.addMessageListener(listenerAdapter, new ChannelTopic(REDIS_SUBSCRIBE_CHANNEL));
        //这个container 可以添加多个 messageListener
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(B2cWeiXinEventHandle handle) {
        return new MessageListenerAdapter(handle, "onMessage");
    }




}