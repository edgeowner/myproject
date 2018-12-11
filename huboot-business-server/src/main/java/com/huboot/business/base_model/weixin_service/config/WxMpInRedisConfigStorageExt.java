package com.huboot.business.base_model.weixin_service.config;

import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.TimeUnit;

/**
 * 基于Redis的微信配置provider
 * <pre>
 *    使用说明：本实现仅供参考，并不完整，
 *    比如为减少项目依赖，未加入redis分布式锁的实现，如有需要请自行实现。
 * </pre>
 * @author nickwong
 */
@SuppressWarnings("hiding")
public class WxMpInRedisConfigStorageExt extends WxMpInMemoryConfigStorage {

  private final static String ACCESS_TOKEN_KEY = "wechat_access_token_";

  private final static String JSAPI_TICKET_KEY = "wechat_jsapi_ticket_";

  private final static String CARDAPI_TICKET_KEY = "wechat_cardapi_ticket_";

  /**
   * 使用连接池保证线程安全
   */
  //protected final JedisPool jedisPool;
  protected final RedisTemplate<String, String> redisTemplate;

  private String accessTokenKey;

  private String jsapiTicketKey;

  private String cardapiTicketKey;

  private String weixinUid;

  public String getWeixinUid() {
    return weixinUid;
  }

  public void setWeixinUid(String weixinUid) {
    this.weixinUid = weixinUid;
  }

  /*public WxMpInRedisConfigStorageExt(JedisPool jedisPool) {
      this.jedisPool = jedisPool;
    }*/
  public WxMpInRedisConfigStorageExt(RedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  /**
   * 每个公众号生成独有的存储key
   *
   * @param appId
   */
  @Override
  public void setAppId(String appId) {
    super.setAppId(appId);
    this.accessTokenKey = ACCESS_TOKEN_KEY.concat(appId);
    this.jsapiTicketKey = JSAPI_TICKET_KEY.concat(appId);
    this.cardapiTicketKey = CARDAPI_TICKET_KEY.concat(appId);
  }

  @Override
  public String getAccessToken() {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      return jedis.get(accessTokenKey);
    }*/
    return redisTemplate.opsForValue().get(accessTokenKey);
  }

  @Override
  public boolean isAccessTokenExpired() {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      return jedis.ttl(accessTokenKey) < 2;
    }*/
    return redisTemplate.getExpire(accessTokenKey, TimeUnit.SECONDS) < 2;
  }

  @Override
  public void updateAccessToken(String accessToken, int expiresInSeconds) {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      jedis.setex(accessTokenKey, expiresInSeconds - 200, accessToken);
    }*/
    long time = Long.valueOf(expiresInSeconds - 200);
    redisTemplate.opsForValue().set(accessTokenKey, accessToken, time, TimeUnit.SECONDS);
  }

  @Override
  public void expireAccessToken() {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      jedis.expire(accessTokenKey, 0);
    }*/
    redisTemplate.expire(accessTokenKey, 0, TimeUnit.SECONDS);
  }

  @Override
  public String getJsapiTicket() {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      return jedis.get(jsapiTicketKey);
    }*/
    return redisTemplate.opsForValue().get(jsapiTicketKey);
  }

  @Override
  public boolean isJsapiTicketExpired() {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      return jedis.ttl(jsapiTicketKey) < 2;
    }*/
    return redisTemplate.getExpire(jsapiTicketKey, TimeUnit.SECONDS) < 2;
  }

  @Override
  public void updateJsapiTicket(String jsapiTicket, int expiresInSeconds) {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      jedis.setex(jsapiTicketKey, expiresInSeconds - 200, jsapiTicket);
    }*/
    long time = Long.valueOf(expiresInSeconds - 200);
    redisTemplate.opsForValue().set(jsapiTicketKey, jsapiTicket, time, TimeUnit.SECONDS);
  }

  @Override
  public void expireJsapiTicket() {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      jedis.expire(jsapiTicketKey, 0);
    }*/
    redisTemplate.expire(jsapiTicketKey, 0, TimeUnit.SECONDS);
  }

  @Override
  public String getCardApiTicket() {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      return jedis.get(cardapiTicketKey);
    }*/
    return redisTemplate.opsForValue().get(cardapiTicketKey);
  }

  @Override
  public boolean isCardApiTicketExpired() {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      return jedis.ttl(cardapiTicketKey) < 2;
    }*/
    return redisTemplate.getExpire(cardapiTicketKey, TimeUnit.SECONDS) < 2;
  }

  @Override
  public void updateCardApiTicket(String cardApiTicket, int expiresInSeconds) {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      jedis.setex(cardapiTicketKey, expiresInSeconds - 200, cardApiTicket);
    }*/
    long time = Long.valueOf(expiresInSeconds - 200);
    redisTemplate.opsForValue().set(cardapiTicketKey, cardApiTicket, time, TimeUnit.SECONDS);
  }

  @Override
  public void expireCardApiTicket() {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      jedis.expire(cardapiTicketKey, 0);
    }*/
    redisTemplate.expire(cardapiTicketKey, 0, TimeUnit.SECONDS);
  }
}
