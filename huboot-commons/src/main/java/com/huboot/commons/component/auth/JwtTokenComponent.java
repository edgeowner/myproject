package com.huboot.commons.component.auth;

import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.config.JwtProperties;
import com.huboot.commons.utils.JsonUtil;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.InvalidSignatureException;
import org.springframework.security.jwt.crypto.sign.MacSigner;

import java.time.LocalDateTime;

/**
 * 验证用户登录权限
 ***/
@Configuration
@EnableConfigurationProperties({JwtProperties.class})
public class JwtTokenComponent {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenComponent.class);

    @Getter
    @Autowired
    private JwtProperties jwtProperties;

    private static final Long SYSTEM_USER_ID = 0L;//系统用户id
    private static final Long SYSTEM_GUID = 0L;//系统全局用户id

    /**
     * 生成token,
     **/
    public String generateToken(JwtClaims jwtClaims) {
        Long seconds = null;
        MacSigner macSigner = null;
        if (jwtClaims.isUser()) {
            seconds = jwtProperties.getUserExpiration();
            macSigner = new MacSigner(jwtProperties.USER_SECRET);
        } else if (jwtClaims.isTaskService()) {
            seconds = jwtProperties.getTaskServiceExpiration();
            macSigner = new MacSigner(jwtProperties.TASK_SERVICE_SECRET);
        } else if (jwtClaims.isAnonymous()) {
            seconds = jwtProperties.getAnonymousExpiration();
            macSigner = new MacSigner(jwtProperties.ANONYMOUS_SECRET);
        } else {
            throw new BizException("JwtClaims无法加密");
        }
        if (jwtClaims.getExp() == null || jwtClaims.getIat() == null) {
            LocalDateTime now = LocalDateTime.now();
            jwtClaims.setIat(now);
            jwtClaims.setExp(now.plusSeconds(seconds));
        }
        return JwtHelper.encode(JsonUtil.toNormalJson(jwtClaims), macSigner).getEncoded();
    }

    /**
     * JwtClaims生成token,
     **/
    public String generateTokenWithHead(JwtClaims jwtClaims) {
        String token = this.generateToken(jwtClaims);
        return jwtProperties.TOKEN_HEAD + token;
    }

    /**
     * JwtClaims生成token,
     **/
    public String generateTokenWithHeadForTaskService() {
        JwtUser jwtUser = new JwtUser(SYSTEM_USER_ID, null, SYSTEM_GUID, JwtClaims.Sub.TaskService.name());
        JwtClaims jwtClaims = new JwtClaims(JwtClaims.Sub.TaskService, jwtUser);
        String token = this.generateToken(jwtClaims);
        return jwtProperties.TOKEN_HEAD + token;
    }

    /**
     * JwtClaims生成token,
     **/
    public String generateTokenWithHeadForAnonymous(String shopSn) {
        JwtUser jwtUser = new JwtUser(null, null, null, JwtClaims.Sub.Anonymous.name());
        jwtUser.setShopSn(shopSn);
        JwtClaims jwtClaims = new JwtClaims(JwtClaims.Sub.Anonymous, jwtUser);
        String token = this.generateToken(jwtClaims);
        return jwtProperties.TOKEN_HEAD + token;
    }

    /**
     * token 添加头部
     **/
    public String generateTokenWithHead(String token) {
        return jwtProperties.TOKEN_HEAD + token;
    }

    /**
     * 解密
     *
     * @param token
     * @return
     */
    public JwtClaims decode(String token) {
        Jwt jwt = JwtHelper.decode(token);
        JwtClaims jwtClaims = JsonUtil.buildNormalMapper().fromJson(jwt.getClaims(), JwtClaims.class);
        return jwtClaims;
    }

    /**
     * 解密&校验签名
     *
     * @param token
     * @return
     */
    public JwtClaims decodeAndVerifySignature(String token) {
        Jwt jwt = JwtHelper.decode(token);
        JwtClaims jwtClaims = JsonUtil.buildNormalMapper().fromJson(jwt.getClaims(), JwtClaims.class);
        try {
            if (jwtClaims.isUser()) {
                jwt.verifySignature(new MacSigner(jwtProperties.USER_SECRET));
                return jwtClaims;
            } else if (jwtClaims.isTaskService()) {
                jwt.verifySignature(new MacSigner(jwtProperties.TASK_SERVICE_SECRET));
                return jwtClaims;
            } else if (jwtClaims.isAnonymous()) {
                jwt.verifySignature(new MacSigner(jwtProperties.ANONYMOUS_SECRET));
                return jwtClaims;
            } else {
                throw new BizException("JwtClaims无法解密");
            }
        } catch (InvalidSignatureException e) {
            throw new BizException("JwtClaims无法解密");
        }
    }

    /**
     * 解密&检验全部（签名、过期时间）
     *
     * @param token
     * @return
     */
    public JwtClaims decodeAndVerify(String token) {
        JwtClaims jwtClaims = this.decodeAndVerifySignature(token);
        if (jwtClaims.isExpired()) {
            throw new BizException("JwtClaims过期");
        }
        return jwtClaims;
    }

    /**
     * 刷新过期时间
     *
     * @param jwtClaims
     * @return
     */
    public String refreshTokenExp(JwtClaims jwtClaims) {
        if (jwtClaims.isExpired()) {
            return null;
        }
        if (jwtClaims.isExpiredHalf()) {
            return generateToken(jwtClaims);
        }
        return null;
    }
}

