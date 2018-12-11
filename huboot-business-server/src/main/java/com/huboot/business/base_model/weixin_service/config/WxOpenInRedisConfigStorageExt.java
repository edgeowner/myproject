package com.huboot.business.base_model.weixin_service.config;

import me.chanjar.weixin.open.api.impl.WxOpenInMemoryConfigStorage;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/007gzs">007</a>
 */
public class WxOpenInRedisConfigStorageExt extends WxOpenInMemoryConfigStorage {

  private final static String COMPONENT_VERIFY_TICKET_KEY = "wechat_component_verify_ticket:";
  private final static String COMPONENT_ACCESS_TOKEN_KEY = "wechat_component_access_token:";

  private final static String AUTHORIZER_REFRESH_TOKEN_KEY = "wechat_authorizer_refresh_token:";
  private final static String AUTHORIZER_ACCESS_TOKEN_KEY = "wechat_authorizer_access_token:";
  private final static String JSAPI_TICKET_KEY = "wechat_jsapi_ticket:";
  private final static String CARD_API_TICKET_KEY = "wechat_card_api_ticket:";


  protected final RedisTemplate<String, String> redisTemplate;
  private String componentVerifyTicketKey;
  private String componentAccessTokenKey;
  private String authorizerRefreshTokenKey;
  private String authorizerAccessTokenKey;
  private String jsapiTicketKey;
  private String cardApiTicket;

  public WxOpenInRedisConfigStorageExt(RedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @Override
  public void setComponentAppId(String componentAppId) {
    super.setComponentAppId(componentAppId);
    componentVerifyTicketKey = COMPONENT_VERIFY_TICKET_KEY.concat(componentAppId);
    componentAccessTokenKey = COMPONENT_ACCESS_TOKEN_KEY.concat(componentAppId);
    authorizerRefreshTokenKey = AUTHORIZER_REFRESH_TOKEN_KEY.concat(componentAppId);
    authorizerAccessTokenKey = AUTHORIZER_ACCESS_TOKEN_KEY.concat(componentAppId);
    jsapiTicketKey = JSAPI_TICKET_KEY.concat(componentAppId);
    cardApiTicket = CARD_API_TICKET_KEY.concat(componentAppId);
  }

  @Override
  public String getComponentVerifyTicket() {
    /*try (Jedis jedis = jedisPool.getResource()) {
      return jedis.get(componentVerifyTicketKey);
    }*/
    return redisTemplate.opsForValue().get(componentVerifyTicketKey);
  }

  @Override
  public void setComponentVerifyTicket(String componentVerifyTicket) {
    /*try (Jedis jedis = jedisPool.getResource()) {
      jedis.set(componentVerifyTicketKey, componentVerifyTicket);
    }*/
    redisTemplate.opsForValue().set(componentVerifyTicketKey, componentVerifyTicket);
  }

  @Override
  public String getComponentAccessToken() {
    /*try (Jedis jedis = jedisPool.getResource()) {
      return jedis.get(componentAccessTokenKey);
    }*/
    return redisTemplate.opsForValue().get(componentAccessTokenKey);
  }

  @Override
  public boolean isComponentAccessTokenExpired() {
    /*try (Jedis jedis = jedisPool.getResource()) {
      return jedis.ttl(componentAccessTokenKey) < 2;
    }*/
    return redisTemplate.getExpire(componentAccessTokenKey, TimeUnit.SECONDS) < 2;
  }

  @Override
  public void updateComponentAccessTokent(String componentAccessToken, int expiresInSeconds) {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      jedis.setex(componentAccessTokenKey, expiresInSeconds - 200, componentAccessToken);
    }*/
    long time = Long.valueOf(expiresInSeconds - 200);
    redisTemplate.opsForValue().set(componentAccessTokenKey, componentAccessToken, time, TimeUnit.SECONDS);
  }

  private String getKey(String prefix, String appId) {
    return prefix.endsWith(":") ? prefix.concat(appId) : prefix.concat(":").concat(appId);
  }

  @Override
  public String getAuthorizerRefreshToken(String appId) {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      return jedis.get(getKey(authorizerRefreshTokenKey, appId));
    }*/
    return redisTemplate.opsForValue().get(getKey(authorizerRefreshTokenKey, appId));
  }

  @Override
  public void setAuthorizerRefreshToken(String appId, String authorizerRefreshToken) {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      jedis.set(getKey(authorizerRefreshTokenKey, appId), authorizerRefreshToken);
    }*/
    redisTemplate.opsForValue().set(getKey(authorizerRefreshTokenKey, appId), authorizerRefreshToken);
  }

  @Override
  public String getAuthorizerAccessToken(String appId) {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      return jedis.get(getKey(authorizerAccessTokenKey, appId));
    }*/
    return redisTemplate.opsForValue().get(getKey(authorizerAccessTokenKey, appId));
  }


  @Override
  public boolean isAuthorizerAccessTokenExpired(String appId) {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      return jedis.ttl(getKey(authorizerAccessTokenKey, appId)) < 2;
    }*/
    return redisTemplate.getExpire(getKey(authorizerAccessTokenKey, appId), TimeUnit.SECONDS) < 2;
  }

  @Override
  public void expireAuthorizerAccessToken(String appId) {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      jedis.expire(getKey(authorizerAccessTokenKey, appId), 0);
    }*/
    redisTemplate.expire(getKey(authorizerAccessTokenKey, appId), 0, TimeUnit.SECONDS);
  }

  @Override
  public void updateAuthorizerAccessToken(String appId, String authorizerAccessToken, int expiresInSeconds) {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      jedis.setex(getKey(authorizerAccessTokenKey, appId), expiresInSeconds - 200, authorizerAccessToken);
    }*/
    long time = Long.valueOf(expiresInSeconds - 200);
    redisTemplate.opsForValue().set(getKey(authorizerAccessTokenKey, appId), authorizerAccessToken, time, TimeUnit.SECONDS);
  }

  @Override
  public String getJsapiTicket(String appId) {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      return jedis.get(getKey(jsapiTicketKey, appId));
    }*/
    return redisTemplate.opsForValue().get(getKey(jsapiTicketKey, appId));
  }

  @Override
  public boolean isJsapiTicketExpired(String appId) {/*
    try (Jedis jedis = this.jedisPool.getResource()) {
      return jedis.ttl(getKey(jsapiTicketKey, appId)) < 2;
    }*/
    return redisTemplate.getExpire(getKey(jsapiTicketKey, appId), TimeUnit.SECONDS) < 2;
  }

  @Override
  public void expireJsapiTicket(String appId) {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      jedis.expire(getKey(jsapiTicketKey, appId), 0);
    }*/
    redisTemplate.expire(getKey(jsapiTicketKey, appId), 0, TimeUnit.SECONDS);
  }

  @Override
  public void updateJsapiTicket(String appId, String jsapiTicket, int expiresInSeconds) {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      jedis.setex(getKey(jsapiTicketKey, appId), expiresInSeconds - 200, jsapiTicket);
    }*/
    long time = Long.valueOf(expiresInSeconds - 200);
    redisTemplate.opsForValue().set(getKey(jsapiTicketKey, appId), jsapiTicket, time, TimeUnit.SECONDS);

  }

  @Override
  public String getCardApiTicket(String appId) {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      return jedis.get(getKey(jsapiTicketKey, appId));
    }*/
    return redisTemplate.opsForValue().get(getKey(jsapiTicketKey, appId));
  }

  @Override
  public boolean isCardApiTicketExpired(String appId) {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      return jedis.ttl(getKey(cardApiTicket, appId)) < 2;
    }*/
    return redisTemplate.getExpire(getKey(cardApiTicket, appId), TimeUnit.SECONDS) < 2;
  }

  @Override
  public void expireCardApiTicket(String appId) {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      jedis.expire(getKey(cardApiTicket, appId), 0);
    }*/
    redisTemplate.expire(getKey(cardApiTicket, appId), 0, TimeUnit.SECONDS);
  }

  @Override
  public void updateCardApiTicket(String appId, String cardApiTicket, int expiresInSeconds) {
    /*try (Jedis jedis = this.jedisPool.getResource()) {
      jedis.setex(getKey(cardApiTicket, appId), expiresInSeconds - 200, cardApiTicket);
    }*/
    long time = Long.valueOf(expiresInSeconds - 200);
    redisTemplate.opsForValue().set(getKey(cardApiTicket, appId), cardApiTicket, time, TimeUnit.SECONDS);
  }
}
