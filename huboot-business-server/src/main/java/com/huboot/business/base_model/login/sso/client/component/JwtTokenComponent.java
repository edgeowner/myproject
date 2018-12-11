package com.huboot.business.base_model.login.sso.client.component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.huboot.business.base_model.login.sso.client.dto.BaseUser;
import com.huboot.business.base_model.login.sso.client.dto.login.RequestHeader;
import com.huboot.business.base_model.login.sso.client.exception.SsoException;
import com.huboot.business.base_model.login.sso.client.inter.ISsoProvider;
import com.huboot.business.base_model.login.sso.client.interceptor.ClientInfoInterceptor;
import com.huboot.business.base_model.login.sso.client.interceptor.RequestInfo;
import com.huboot.business.base_model.login.sso.client.secruity.UserFactory;
import com.huboot.business.base_model.login.sso.client.utils.WebUtil;
import com.huboot.business.base_model.login.sso.client.utils.encrypt.AESUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 验证用户登录权限
 * ***/
@Component
public class JwtTokenComponent {
	


	private static final Logger logger =  LoggerFactory.getLogger(JwtTokenComponent.class);
    
    public static final String CLAIM_KEY_ATTACH = "attach";//base user info
    
    public static final String CLAIM_KEY_BID = "bid";//业务系统id

    public static final String CLAIM_KEY_ROLES = "roles";//用户权限

    public static final String CLAIM_KEY_SHOP_UID = "shopUid";//店铺id

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;
    
    @Value("${sso.fromSys}")
    private String fromSys;
    
    @Value("${sso.bidProviderClass:bidProviderClass}")
    private String bidClassProvid;

    @Autowired
    @Qualifier("objectMapper")
    private ObjectMapper mapper;
    
    /**
     * 获取用户名
     * **/
    public String getUsernameFromToken(Claims claims) {
        return  claims.getSubject();
    }
    
    @SuppressWarnings("unchecked")
	public List<String> getUserRoles(Claims claims){
    	return (List<String>) claims.get(CLAIM_KEY_ROLES);
    }
    
    /**
     * 获取一个用于认证的UserDetails
     * **/
    public UserDetails getUserDetails(Claims claims) throws JsonParseException, JsonMappingException, IOException{
    	return UserFactory.create(getBaseUser(claims),getUserRoles(claims));
    }
    
    /**
     * 获取用户基础信息
     * **/
    public BaseUser getBaseUser(Claims claims) throws JsonParseException, JsonMappingException, IOException{
    	String att = (String) claims.get(CLAIM_KEY_ATTACH);
    	String json = AESUtil.decrypt(Base64.getDecoder().decode(att), AESUtil.getRawKey(secret.getBytes()));
    	BaseUser baseUser = mapper.readValue(json, BaseUser.class);
    	return baseUser;
    }
    
    /**
     * 获取业务系统自己的用户id
     * **/
    public String getBid(Claims claims){
    	return (String) claims.get(CLAIM_KEY_BID);
    }
    
    /**
     * 获取业务系统标识
     * **/
    public String getCurrentSys(Claims claims){
    	return claims.getAudience();
    }
    
    /**
     * 获取jwt sign
     * **/
    public String getSign(Claims claims){
    	return claims.getId();
    }

    /**
     * 获取过期时间
     * **/
    public Date getExpirationDateFromToken(Claims claims) {
        Date expiration;
        try {
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    
    /**
     * 解析token,返回一个Claims.<br />
     * 该方法不要频繁调用,解密是又一定性能消耗的
     * **/
    public Claims parseToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
            claims = null;
        }
        return claims;
    }




    /**
     * 生成过期时间
     * **/
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    
    /**
     * 判断token是否过期
     * **/
    public Boolean isTokenExpired(Claims claims) {
        final Date expiration = getExpirationDateFromToken(claims);
        logger.info("token 过期时间为:{}",expiration);
        return expiration.before(new Date());
    }

    /**
     * 生成token
     * **/
    public String generateToken(BaseUser userDetails,Map<String,String> header,List<String> roles,String bid,Date expirationData) throws UnsupportedEncodingException, JsonProcessingException {
        Map<String, Object> claims = generateClaims(userDetails, header, roles, bid);
        return generateToken(claims,expirationData);
    }

    /**
     * 生成token
     * **/
    public String generateToken(BaseUser userDetails,Map<String,String> header,List<String> roles,String bid) throws UnsupportedEncodingException, JsonProcessingException {
        Map<String, Object> claims = generateClaims(userDetails, header, roles, bid);
        return generateToken(claims);
    }

    private Map<String, Object> generateClaims(BaseUser userDetails, Map<String, String> header, List<String> roles, String bid) throws UnsupportedEncodingException, JsonProcessingException {
        Map<String, Object> claims = new HashMap<>();
        claims.put(Claims.SUBJECT, userDetails.getAccount());
        //write sign
        String str = WebUtil.toQueryString(header, true);
        logger.info("待签名数据:{}",str);
        String sign = DigestUtils.md5DigestAsHex(str.getBytes());
        claims.put(Claims.ID,sign);
        //write user info
        byte[] attche = AESUtil.encrypt(AESUtil.getRawKey(secret.getBytes()), mapper.writeValueAsString(userDetails));
        claims.put(CLAIM_KEY_ATTACH, Base64.getEncoder().encodeToString(attche));
        //write bid
        claims.put(CLAIM_KEY_BID, bid);
        //write sys info
        claims.put(Claims.AUDIENCE, fromSys);//接收jwt的一方
        //write sign time
        claims.put(Claims.ISSUED_AT, LocalDateTime.now().toString());//jwt的签发时间
        //write user rols
        claims.put(CLAIM_KEY_ROLES, roles);
        String shopUid = (String) RequestInfo.get(ClientInfoInterceptor.SHOP_UID);
        claims.put(CLAIM_KEY_SHOP_UID,shopUid);
        return claims;
    }

    /**
     * 刷新认证信息过期时间
     * **/
    public String refreshAuth(Claims claims){
    	return generateToken(claims);
    }
    
    /**
     * 生成token
     * **/
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 生成token
     * **/
    private String generateToken(Map<String, Object> claims,Date expirationData) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationData)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    
    
    /**
     * 签名检查
     * @throws UnsupportedEncodingException 
     * @throws IllegalAccessException 
     * **/
    public boolean validateSign(RequestHeader requestHeader, Claims claims) throws UnsupportedEncodingException{
        String sign = getSign(claims);
		String str = "";
		try {
			str = WebUtil.toQueryString(WebUtil.beanToMapWithString(requestHeader, false), true);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
            logger.error("解析RequestHeader失败",e);
		}
		if(logger.isDebugEnabled())logger.debug("待签名数据:{}",str);
		String hash = DigestUtils.md5DigestAsHex(str.getBytes());
		if(sign.equals(hash)){
			return true;
		}
        return false;
    }

    public static void main(String[] args) {
         String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxODUxNjI1NTYwMSIsImF1ZCI6IndlaXhpbl9hcHAiLCJyb2xlcyI6WyJST0xFX01JTklBUFBfVVNFUl9DT01NT04iXSwiYXR0YWNoIjoieCt6UTZFTkFBZTl6ejNsZVNucGtCeE9PM3dSRHNkZU8rbzN2ZkZPZnp2c3dETVBmSk5XK2tLalJNUElKaDJUMENZSk1kMEVURWc4MXpmd215TmQwQXZObG5mU3MzNjVSakpnbXhwU2xOMUR1UW1XRktYT2FESHgxTkU5ZTNyU25QYVhuL1ZxSFdGK3NuK1dXYW8rZER4OGx3MmZWeWk4czBCajBqbXdQZzhKaW5OZmhxNHpKaGZQb1ZPbUxKV2dlZml4ZmdCME55RzZGZmNzZnJPVEU3dz09IiwiYmlkIjoiOCIsImV4cCI6MTgzNzMwNjUzMiwiaWF0IjoiMjAxOC0wMy0yMlQxMDo1NTozMi41MTkiLCJqdGkiOiJkMzI3ZDk4NTkwZTU1MGUwZTQxMWIwMzA3NDY2NWE4NSJ9.qfwPnY6cC43oPcG6LIYqk-cQE332i0SXiWrkhINN2IvV59DwrooctxetxKF2CiOnB7tpAq9tPVOT89ZY1Pt8vw";
        //String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxODUxNjI1NTYwMSIsImF1ZCI6IndlaXhpbl9hcHAiLCJyb2xlcyI6WyJST0xFX01JTklBUFBfVVNFUl9DT01NT04iXSwiYXR0YWNoIjoieCt6UTZFTkFBZTl6ejNsZVNucGtCeE9PM3dSRHNkZU8rbzN2ZkZPZnp2c3dETVBmSk5XK2tLalJNUElKaDJUMENZSk1kMEVURWc4MXpmd215TmQwQXZObG5mU3MzNjVSakpnbXhwU2xOMUR1UW1XRktYT2FESHgxTkU5ZTNyU25QYVhuL1ZxSFdGK3NuK1dXYW8rZER4OGx3MmZWeWk4czBCajBqbXdQZzhKaW5OZmhxNHpKaGZQb1ZPbUxKV2dlZml4ZmdCME55RzZGZmNzZnJPVEU3dz09IiwiYmlkIjoiOCIsImV4cCI6MTgzNzMwNjUzMiwiaWF0IjoiMjAxOC0wMy0yMlQxMDo1NTozMi41MTkiLCJqdGkiOiJkMzI3ZDk4NTkwZTU1MGUwZTQxMWIwMzA3NDY2NWE4NSJ9.qfwPnY6cC43oPcG6LIYqk-cQE332i0SXiWrkhINN2IvV59DwrooctxetxKF2CiOnB7tpAq9tPVOT89ZY1Pt8vw";
        //String token ="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxODYyMzEwMTc4MyIsImF1ZCI6IndlaXhpbl9hcHAiLCJyb2xlcyI6WyJST0xFX01JTklBUFBfVVNFUl9DT01NT04iXSwiYXR0YWNoIjoiRXZzMHN4R0RsU1llQVNDWFNwcWRaSm1pZW9YR1puaDYrM0FvWjBMTXhpQ0VSUUtFSC9PQmlqdjdVQ2hzdkdjUlJibm9lUGY4U29PNW52T2FqL241aWZObG5mU3MzNjVSakpnbXhwU2xOMUR1UW1XRktYT2FESHgxTkU5ZTNyU25QYVhuL1ZxSFdGK3NuK1dXYW8rZER4OGx3MmZWeWk4czBCajBqbXdQZzhKaW5OZmhxNHpKaGZQb1ZPbUxKV2dlR25jZHZ3N3pUa3VLUXJpWnZpdFlxQT09IiwiYmlkIjoiNiIsImV4cCI6MTgzNzMyMzIxNiwiaWF0IjoiMjAxOC0wMy0yMlQxNTozMzozNi45ODYiLCJqdGkiOiI0NzU1MTBiZjFhNjliNmM5NmI1YTNlZjY1ZDYxNjNjNiJ9.jG2RTZ4LDstqwfwe1hbfxTd2--M4ayVgupRnX5D8YJ6TKqPkrGF_2plK6gJEBLsm-Ba93ISK7JIMH3pVgdGBtA";
        String secret ="mySecret";
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
            claims = null;
        }
        String sign =new JwtTokenComponent().getSign(claims);
        System.out.println(sign);
       // String h = "acceptEncoding=gzip&connection=Keep-Alive&userAgent=Mozilla%2F5.0+%28Linux%3B+Android+5.1%3B+MX5+Build%2FLMY47I%3B+wv%29+AppleWebKit%2F537.36+%28KHTML%2C+like+Gecko%29+Version%2F4.0+Chrome%2F57.0.2987.132+MQQBrowser%2F6.2+TBS%2F043909+Mobile+Safari%2F537.36+MicroMessenger%2F6.6.5.1280%280x26060533%29+NetType%2F4G+Language%2Fzh_CN+MicroMessenger%2F6.6.5.1280%280x26060533%29+NetType%2F4G+Language%2Fzh_CN";
       String h = "acceptEncoding=gzip&connection=Keep-Alive&userAgent=Mozilla%2F5.0+%28Linux%3B+Android+7.1.1%3B+MI+MAX+2+Build%2FNMF26F%3B+wv%29+AppleWebKit%2F537.36+%28KHTML%2C+like+Gecko%29+Version%2F4.0+Chrome%2F57.0.2987.132+MQQBrowser%2F6.2+TBS%2F043909+Mobile+Safari%2F537.36+MicroMessenger%2F6.6.5.1280%280x26060533%29+NetType%2FWIFI+Language%2Fzh_CN+MicroMessenger%2F6.6.5.1280%280x26060533%29+NetType%2FWIFI+Language%2Fzh_CN";
        String hash = DigestUtils.md5DigestAsHex(h.getBytes());
        System.out.println(DigestUtils.md5DigestAsHex(h.getBytes()));
        System.out.println(hash);
    }

    
    /***
     * 当跨系统时，需要重置bid和rolse
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     * ***/
    public Claims overWriteBidAndRoles(Claims claims) throws JsonParseException, JsonMappingException, IOException{
    	//认证信息跨业务系统传输了,则需要更新认证信息中的bid
		if(StringUtils.isEmpty(bidClassProvid) || bidClassProvid.equals("bidProviderClass")){
			if(claims.containsKey(CLAIM_KEY_BID)){
				claims.remove(CLAIM_KEY_BID);
			}
			claims.put(CLAIM_KEY_BID, "");
			return claims;
		}
		try {
			String gid = getBaseUser(claims).getGid();
			@SuppressWarnings("unchecked")
			Class<ISsoProvider> classz = (Class<ISsoProvider>) Class.forName(bidClassProvid);
			ISsoProvider provider = classz.newInstance();
			//write bid
			String bid = provider.getBussiessId(gid);
			if(claims.containsKey(CLAIM_KEY_BID)){
				claims.remove(CLAIM_KEY_BID);
			}
			claims.put(CLAIM_KEY_BID, bid);
			//write roles
			List<String> rolse = provider.getUserRolse(gid);
			if(claims.containsKey(CLAIM_KEY_ROLES)){
				claims.remove(CLAIM_KEY_ROLES);
			}
			claims.put(CLAIM_KEY_ROLES, rolse);
			return claims;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
			// TODO Auto-generated catch block
			logger.error("类路:" + bidClassProvid + "径不存在或转换出错，请检查");
            ex.printStackTrace();
        }
		throw new SsoException(bidClassProvid + "转换失败");
    }
    
    /**
     * 获取用户角色,通过反射
     * **/
    public List<String> getUserRoles(String gid){
    	try {
			@SuppressWarnings("unchecked")
			Class<ISsoProvider> classz = (Class<ISsoProvider>) Class.forName(bidClassProvid);
			ISsoProvider provider = classz.newInstance();
			List<String> roles = provider.getUserRolse(gid);
			return roles;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
			// TODO Auto-generated catch block
			logger.error("类路:" + bidClassProvid + "径不存在或转换出错，请检查");
		}
		throw new SsoException(bidClassProvid + "转换失败");
    }
    
    /**
     * 获取用户角色,通过反射
     * **/
    public String getBid(String gid){
    	try {
    		@SuppressWarnings("unchecked")
			Class<ISsoProvider> classz = (Class<ISsoProvider>) Class.forName(bidClassProvid);
			ISsoProvider provider = classz.newInstance();
			//write bid
			String bid = provider.getBussiessId(gid);
			return bid;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
			// TODO Auto-generated catch block
			logger.error("类路:" + bidClassProvid + "径不存在或转换出错，请检查");
		}
		throw new SsoException(bidClassProvid + "转换失败");
    }



}

