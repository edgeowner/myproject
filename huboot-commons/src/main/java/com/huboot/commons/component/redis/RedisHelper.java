package com.huboot.commons.component.redis;

import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.utils.AppAssert;
import com.huboot.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/9/7 0007.
 */
@Slf4j
@Component
@ConditionalOnClass(RedisTemplate.class)
public class RedisHelper {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final RedisSerializer<String> stringSerializer = new StringRedisSerializer();

    private ConcurrentHashMap<String, String> lockMap = new ConcurrentHashMap();

    public static final String UNLOCK_LUA;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        UNLOCK_LUA = sb.toString();
    }

    /**
     * 获取非阻塞锁
     * @param lockKey
     * @param expireTime 秒数
     * @return
     */
    public String tryNoBlockLock(String lockKey, long expireTime) {
        String lockId = UUID.randomUUID().toString().replace("-", "");
        try {
            Boolean success = redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    RedisStringCommands commands = redisConnection.stringCommands();
                    return commands.set(stringSerializer.serialize(lockKey), stringSerializer.serialize(lockId),
                            Expiration.from(expireTime, TimeUnit.SECONDS), RedisStringCommands.SetOption.SET_IF_ABSENT);
                }
            });
            if(success) {
                log.info("redis(lockKey:" + lockKey + ",lockId:" + lockId + ")加锁成功");
                lockMap.put(lockKey, lockId);
                return lockId;
            } else {
                if(log.isDebugEnabled()) {
                    log.debug("redis(lockKey:" + lockKey + ",lockId:" + lockId + ")加锁失败");
                }
                return null;
            }
        } catch (Exception e) {
            log.error("redis(lockKey:" + lockKey + ",lockId:" + lockId + ")加锁异常：", e);
            throw new BizException("获取锁异常");
        }
    }

    /**
     * 获取阻塞锁,不可重入的，严禁在获取锁的里面再次获取同一个lockKey的所
     * 默认30秒获取不到所就返回
     * @param lockKey
     * @param expireTime 锁的过期时间 秒数
     * @return
     */
    public String tryBlockLock(String lockKey, long expireTime) {
        return this.tryBlockLock(lockKey, expireTime, 30);
    }

    /**
     * 获取阻塞锁,不可重入的，严禁在获取锁的里面再次获取同一个lockKey的所
     * @param lockKey
     * @param lockExpireTime 锁的过期时间(秒数)
     * @param queueExpireTime 排队过期时间(秒数)
     * @return
     */
    public String tryBlockLock(String lockKey, long lockExpireTime, long queueExpireTime) {
        String lockId = this.tryNoBlockLock(lockKey, lockExpireTime);
        long start = System.currentTimeMillis();
        queueExpireTime = queueExpireTime * 1000;
        while (StringUtils.isEmpty(lockId)) {
            long end = System.currentTimeMillis();
            if((end - start) >= queueExpireTime) {
                return null;
            }
            /**
             * 正在持锁中，不需要进行尝试获取锁，
             * 如果请求分发在同一个服务器上，这样就可以减少锁竞争，
             */
            if(!StringUtils.isEmpty(lockMap.get(lockKey))) {
                continue;
            }
            lockId = this.tryNoBlockLock(lockKey, lockExpireTime);
        }
        return lockId;
    }

    /**
     * 解锁
     * @param lockKey
     */
    public void unLock(String lockKey, String lockId) {
        try {
            List<String> keys = new ArrayList<>();
            keys.add(lockKey);
            String[] args = new String[1];
            args[0] = lockId;
            Long result = redisTemplate.execute(new DefaultRedisScript<>(UNLOCK_LUA, Long.class), keys, args);
            if(result == null) {
                result = -1l;
            }
            log.info("redis(lockKey:" + lockKey + ",lockId:" + lockId + ")解锁结果: " + result);
        } catch (Exception e) {
            log.error("redis(lockKey:" + lockKey + ",lockId:" + lockId + ")解锁异常：", e);
        } finally {
            lockMap.remove(lockKey);
        }
    }

    /**
     * 发送队列消息
     * @param queue
     * @param obj
     * @param <T>
     */
    public <T> void sendMessage(String queue, T obj) {
        AppAssert.notEmpty(queue, "队列名称不能为空");
        AppAssert.notNull(obj, "消息不能为空");
        String json = JsonUtil.buildNormalMapper().toJson(obj);
        redisTemplate.boundListOps(queue).leftPush(json);
    }

    /**
     * 广播消息
     * @param topic
     * @param obj
     * @param <T>
     */
    public <T> void broadcastMessage(String topic, T obj) {
        AppAssert.notEmpty(topic, "topic名称不能为空");
        AppAssert.notNull(obj, "消息不能为空");
        String json = JsonUtil.buildNormalMapper().toJson(obj);
        redisTemplate.convertAndSend(topic, json);
    }
}
