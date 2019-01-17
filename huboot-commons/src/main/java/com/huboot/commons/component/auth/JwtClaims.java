package com.huboot.commons.component.auth;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

@Data
public class JwtClaims implements Serializable {

    public JwtClaims() {

    }

    public JwtClaims(Sub sub, JwtUser user) {
        this.sub = sub;
        this.user = user;
    }

    /**
     * https://www.jianshu.com/p/bc8d48842eea
     * jwt的token签发时间,如果过期时间到了一般，进行刷新
     *
     * @return the iat
     */
    private LocalDateTime iat;

    /**
     * https://www.jianshu.com/p/bc8d48842eea
     * jwt的token过期时间,如果过期时间到了一般，进行刷新
     *
     * @return the exp
     * Expiration Time
     */
    private LocalDateTime exp;
    /**
     * sub
     * Subject
     */
    private Sub sub;
    /**
     * 携带的用户信息
     */
    private JwtUser user;

    @AllArgsConstructor
    @Getter
    public enum Sub implements BaseEnum {
        User("登陆用户"), TaskService("定时任务"), Anonymous("匿名用户"),;
        private String showName;
    }

    public Boolean isExpired() {
        return LocalDateTime.now().isAfter(exp);
    }

    /**
     * token有效时间过半
     *
     * @return
     */
    Boolean isExpiredHalf() {
        //token设置的有效期
        Duration jwtDuration = Duration.between(iat, exp);
        //当前时间与结束时间差
        Duration nowDuration = Duration.between(LocalDateTime.now(), exp);

        return nowDuration.toMillis() < (jwtDuration.toMillis() / 2);
    }

    public Boolean isUser() {
        return this.sub.equals(Sub.User);
    }

    public Boolean isTaskService() {
        return this.sub.equals(Sub.TaskService);
    }

    public Boolean isAnonymous() {
        return this.sub.equals(Sub.Anonymous);
    }
}
