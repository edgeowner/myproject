package com.huboot.user.common.cache;

import com.huboot.commons.utils.CommonTools;
import com.huboot.share.common.constant.RedisHashName;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/9/8 0008.
 */
@Component
public class UserDataProxy {

    //缓存验证码code，10min
    @Cacheable(value = RedisHashName.USER_SERVICE_AUTH_LOGIN_CODE_ZKSHOP, keyGenerator = "keyGenerator", cacheManager = "redisCacheManager", unless = "#result == null")
    public String randomCodeForZkShop(String phone) {
        return CommonTools.getRandomNumber(6);
    }

    //缓存验证码code，10min
    @Cacheable(value = RedisHashName.USER_SERVICE_AUTH_LOGIN_CODE_ZKUSER, keyGenerator = "keyGenerator", cacheManager = "redisCacheManager", unless = "#result == null")
    public String randomCodeForZkUser(String phone) {
        return CommonTools.getRandomNumber(6);
    }

    //缓存验证码code，10min
    @Cacheable(value = RedisHashName.USER_SERVICE_AUTH_LOGIN_CODE_ZKSHOP, keyGenerator = "keyGenerator", cacheManager = "redisCacheManager", unless = "#result == null")
    public String getRandomCodeForZkShop(String phone) {
        return null;
    }

    //缓存验证码code，10min
    @Cacheable(value = RedisHashName.USER_SERVICE_AUTH_LOGIN_CODE_ZKUSER, keyGenerator = "keyGenerator", cacheManager = "redisCacheManager", unless = "#result == null")
    public String getRandomCodeForZkUser(String phone) {
        return null;
    }
}
