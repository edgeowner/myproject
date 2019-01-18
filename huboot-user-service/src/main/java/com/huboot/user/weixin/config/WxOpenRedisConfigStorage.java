package com.huboot.user.weixin.config;

import me.chanjar.weixin.open.api.impl.WxOpenInMemoryConfigStorage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/007gzs">007</a>
 */
public class WxOpenRedisConfigStorage extends WxOpenInMemoryConfigStorage {
  private final static String COMPONENT_VERIFY_TICKET_KEY = "wechat_component_verify_ticket:";
  private final static String COMPONENT_ACCESS_TOKEN_KEY = "wechat_component_access_token:";

  private final static String AUTHORIZER_REFRESH_TOKEN_KEY = "wechat_authorizer_refresh_token:";
  private final static String AUTHORIZER_ACCESS_TOKEN_KEY = "wechat_authorizer_access_token:";
  private final static String JSAPI_TICKET_KEY = "wechat_jsapi_ticket:";
  private final static String CARD_API_TICKET_KEY = "wechat_card_api_ticket:";

  protected final RedisTemplate<String, String> redisTemplate;
  /**
   * redis 存储的 key 的前缀，可为空
   */
  private String keyPrefix;
  private String componentVerifyTicketKey;
  private String componentAccessTokenKey;
  private String authorizerRefreshTokenKey;
  private String authorizerAccessTokenKey;
  private String jsapiTicketKey;
  private String cardApiTicket;

  public WxOpenRedisConfigStorage(RedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public WxOpenRedisConfigStorage(RedisTemplate redisTemplate, String keyPrefix) {
    this.redisTemplate = redisTemplate;
    this.keyPrefix = keyPrefix;
  }

  @Override
  public void setComponentAppId(String componentAppId) {
    super.setComponentAppId(componentAppId);
    String prefix = StringUtils.isBlank(keyPrefix) ? "" :
      (StringUtils.endsWith(keyPrefix, ":") ? keyPrefix : (keyPrefix + ":"));
    componentVerifyTicketKey = prefix + COMPONENT_VERIFY_TICKET_KEY.concat(componentAppId);
    componentAccessTokenKey = prefix + COMPONENT_ACCESS_TOKEN_KEY.concat(componentAppId);
    authorizerRefreshTokenKey = prefix + AUTHORIZER_REFRESH_TOKEN_KEY.concat(componentAppId);
    authorizerAccessTokenKey = prefix + AUTHORIZER_ACCESS_TOKEN_KEY.concat(componentAppId);
    this.jsapiTicketKey = JSAPI_TICKET_KEY.concat(componentAppId);
    this.cardApiTicket = CARD_API_TICKET_KEY.concat(componentAppId);
  }

  @Override
  public String getComponentVerifyTicket() {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      return jedis.get(this.componentVerifyTicketKey);
    }*/
    return redisTemplate.opsForValue().get(this.componentVerifyTicketKey);
  }

  @Override
  public void setComponentVerifyTicket(String componentVerifyTicket) {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      jedis.set(this.componentVerifyTicketKey, componentVerifyTicket);
    }*/
    redisTemplate.opsForValue().set(this.componentVerifyTicketKey, componentVerifyTicket);
  }

  @Override
  public String getComponentAccessToken() {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      return jedis.get(this.componentAccessTokenKey);
    }*/
    return redisTemplate.opsForValue().get(this.componentAccessTokenKey);
  }

  @Override
  public boolean isComponentAccessTokenExpired() {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      return jedis.ttl(this.componentAccessTokenKey) < 2;
    }*/
    return redisTemplate.getExpire(this.componentAccessTokenKey, TimeUnit.SECONDS) < 2;
  }

  @Override
  public void expireComponentAccessToken(){
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      jedis.expire(this.componentAccessTokenKey, 0);
    }*/
    redisTemplate.expire(this.componentAccessTokenKey, 0, TimeUnit.SECONDS);
  }

  @Override
  public void updateComponentAccessTokent(String componentAccessToken, int expiresInSeconds) {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      jedis.setex(this.componentAccessTokenKey, expiresInSeconds - 200, componentAccessToken);
    }*/
    long time = Long.valueOf(expiresInSeconds - 200);
    redisTemplate.opsForValue().set(this.componentAccessTokenKey, componentAccessToken, time, TimeUnit.SECONDS);
  }

  private String getKey(String prefix, String appId) {
    return prefix.endsWith(":") ? prefix.concat(appId) : prefix.concat(":").concat(appId);
  }

  @Override
  public String getAuthorizerRefreshToken(String appId) {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      return jedis.get(this.getKey(this.authorizerRefreshTokenKey, appId));
    }*/
    return redisTemplate.opsForValue().get(this.getKey(this.authorizerRefreshTokenKey, appId));
  }

  @Override
  public void setAuthorizerRefreshToken(String appId, String authorizerRefreshToken) {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      jedis.set(this.getKey(this.authorizerRefreshTokenKey, appId), authorizerRefreshToken);
    }*/
    redisTemplate.opsForValue().set(this.getKey(this.authorizerRefreshTokenKey, appId), authorizerRefreshToken);
  }

  @Override
  public String getAuthorizerAccessToken(String appId) {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      return jedis.get(this.getKey(this.authorizerAccessTokenKey, appId));
    }*/
    return redisTemplate.opsForValue().get(this.getKey(this.authorizerAccessTokenKey, appId));
  }

  @Override
  public boolean isAuthorizerAccessTokenExpired(String appId) {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      return jedis.ttl(this.getKey(this.authorizerAccessTokenKey, appId)) < 2;
    }*/
    return redisTemplate.getExpire(this.getKey(this.authorizerAccessTokenKey, appId), TimeUnit.SECONDS) < 2;
  }

  @Override
  public void expireAuthorizerAccessToken(String appId) {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      jedis.expire(this.getKey(this.authorizerAccessTokenKey, appId), 0);
    }*/
    redisTemplate.expire(this.getKey(this.authorizerAccessTokenKey, appId), 0, TimeUnit.SECONDS);
  }

  @Override
  public void updateAuthorizerAccessToken(String appId, String authorizerAccessToken, int expiresInSeconds) {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      jedis.setex(this.getKey(this.authorizerAccessTokenKey, appId), expiresInSeconds - 200, authorizerAccessToken);
    }*/
    long time = Long.valueOf(expiresInSeconds - 200);
    redisTemplate.opsForValue().set(this.getKey(this.authorizerAccessTokenKey, appId), authorizerAccessToken, time, TimeUnit.SECONDS);
  }

  @Override
  public String getJsapiTicket(String appId) {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      return jedis.get(this.getKey(this.jsapiTicketKey, appId));
    }*/
    return redisTemplate.opsForValue().get(this.getKey(this.jsapiTicketKey, appId));
  }

  @Override
  public boolean isJsapiTicketExpired(String appId) {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      return jedis.ttl(this.getKey(this.jsapiTicketKey, appId)) < 2;
    }*/
    return redisTemplate.getExpire(this.getKey(this.jsapiTicketKey, appId), TimeUnit.SECONDS) < 2;
  }

  @Override
  public void expireJsapiTicket(String appId) {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      jedis.expire(this.getKey(this.jsapiTicketKey, appId), 0);
    }*/
    redisTemplate.expire(this.getKey(this.jsapiTicketKey, appId), 0, TimeUnit.SECONDS);
  }

  @Override
  public void updateJsapiTicket(String appId, String jsapiTicket, int expiresInSeconds) {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      jedis.setex(this.getKey(this.jsapiTicketKey, appId), expiresInSeconds - 200, jsapiTicket);
    }*/
    long time = Long.valueOf(expiresInSeconds - 200);
    redisTemplate.opsForValue().set(this.getKey(this.jsapiTicketKey, appId), jsapiTicket, time, TimeUnit.SECONDS);
  }

  @Override
  public String getCardApiTicket(String appId) {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      return jedis.get(this.getKey(this.cardApiTicket, appId));
    }*/
    return redisTemplate.opsForValue().get(this.getKey(this.cardApiTicket, appId));
  }

  @Override
  public boolean isCardApiTicketExpired(String appId) {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      return jedis.ttl(this.getKey(this.cardApiTicket, appId)) < 2;
    }*/
    return redisTemplate.getExpire(this.getKey(this.cardApiTicket, appId), TimeUnit.SECONDS) < 2;
  }

  @Override
  public void expireCardApiTicket(String appId) {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      jedis.expire(this.getKey(this.cardApiTicket, appId), 0);
    }*/
    redisTemplate.expire(this.getKey(this.cardApiTicket, appId), 0, TimeUnit.SECONDS);
  }

  @Override
  public void updateCardApiTicket(String appId, String cardApiTicket, int expiresInSeconds) {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      jedis.setex(this.getKey(this.cardApiTicket, appId), expiresInSeconds - 200, cardApiTicket);
    }*/
    long time = Long.valueOf(expiresInSeconds - 200);
    redisTemplate.opsForValue().set(this.getKey(this.cardApiTicket, appId), cardApiTicket, time, TimeUnit.SECONDS);
  }
}
