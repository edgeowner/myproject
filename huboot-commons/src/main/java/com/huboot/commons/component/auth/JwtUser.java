package com.huboot.commons.component.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

/**
 * 序列化时，忽略的属性  @JsonIgnore
 */
@Data
public class JwtUser {

    private static final long serialVersionUID = 1L;

    private Long userId;//用户id
    private Long organizationId;//用户的根组织id,//2019版直客，用户的organizationId是平台的，和employee的organizationId不同；
    private String shopSn;//访问店铺sn，与organizationId对应-仅限匿名用户用这个信息--网约车
    private Long guId;//全局用户id
    private String username;//用户名

    public JwtUser() {

    }

    public JwtUser(Long userId, Long organizationId, Long guId, String username) {
        this.userId = userId;
        this.organizationId = organizationId;
        this.guId = guId;
        this.username = username;
    }

    @JsonIgnore
    private Collection<SimpleGrantedAuthority> authorities;//角色

    @JsonIgnore
    public String getPassword() {
        return null;
    }

    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

}
