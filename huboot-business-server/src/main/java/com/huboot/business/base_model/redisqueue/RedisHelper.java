package com.huboot.business.base_model.redisqueue;

import com.huboot.business.common.utils.AppAssert;
import com.huboot.business.common.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/9/7 0007.
 */
@Component
@ConditionalOnClass(RedisTemplate.class)
public class RedisHelper {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 获取非阻塞锁
     * @param lockKey
     * @param expireTime
     * @return
     */
    public boolean tryNoBlockLock(String lockKey, int expireTime) {
        return false;
    }

    /**
     * 获取阻塞锁
     * @param lockKey
     * @param expireTime
     * @return
     */
    public boolean tryBlockLock(String lockKey, int expireTime) {
        return false;
    }

    /**
     * 解锁
     * @param lockKey
     */
    public void unLock(String lockKey) {

    }

    public <T> void sendMessage(String queue, T obj) {
        AppAssert.notEmpty(queue, "队列名称不能为空");
        AppAssert.notNull(obj, "消息不能为空");
        String json = JsonUtil.buildNormalMapper().toJson(obj);
        redisTemplate.boundListOps(queue).leftPush(json);
    }
}
