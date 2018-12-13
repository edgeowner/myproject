package com.huboot.business.mybatis;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by tonymac on 14/12/1.
 */
public class User implements UserDetails, Serializable {
    private static final long serialVersionUID = -7099710160266016039L;
    // 用户ID
    private Long userId;
    private String username;
    private String password;
    private String phone;
    private String realIp;
    //	private String token;
    private String realName;
    // 微信openid
    private String weixinOpenid;
    // 风控小程序微信openId
    private String miniRiskOpenId;
    /*// 单点登录cookie 值
    private String ssoCookie;*/
    // 属性信息
    private Collection<GrantedAuthority> authorities;

    //微信商城
    private LoginCustomer loginCustomer;
    //内管系统
    private LoginAdmin loginAdmin;
    /**
     * @see SMSDTO.SystemEnum
     * zk(1, "直客系统"),
     miniapp(2,"微信小程序"),
     thdc(3,"同行调车"),
     miniRisk(4,"风控微信小程序");
     */
    private Integer system;

    public User() {
    }

    public User(Long userId, String username, String password, Collection<GrantedAuthority> auths) {
        setUserId(userId);
        setUsername(username);
        setPassword(password);
        this.authorities = auths;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealIp() {
        return realIp;
    }

    public void setRealIp(String realIp) {
        this.realIp = realIp;
    }

    public String getWeixinOpenid() {
        return weixinOpenid;
    }

    public void setWeixinOpenid(String weixinOpenid) {
        this.weixinOpenid = weixinOpenid;
    }

    public String getMiniRiskOpenId() {
        return miniRiskOpenId;
    }

    public void setMiniRiskOpenId(String miniRiskOpenId) {
        this.miniRiskOpenId = miniRiskOpenId;
    }

    public Integer getSystem() {
        return system;
    }

    public void setSystem(Integer system) {
        this.system = system;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public LoginAdmin getLoginAdmin() {
        if(loginAdmin == null){
            loginAdmin = new LoginAdmin();
        }
        return loginAdmin;
    }

    public void setLoginAdmin(LoginAdmin loginAdmin) {
        this.loginAdmin = loginAdmin;
    }

    public LoginCustomer getLoginCustomer() {
        if(loginCustomer == null){
            loginCustomer = new LoginCustomer();
        }
        return loginCustomer;
    }

    public void setLoginCustomer(LoginCustomer loginCustomer) {
        this.loginCustomer = loginCustomer;
    }
}
