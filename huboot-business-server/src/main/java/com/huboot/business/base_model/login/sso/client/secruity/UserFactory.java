package com.huboot.business.base_model.login.sso.client.secruity;

import com.huboot.business.base_model.login.sso.client.dto.BaseUser;
import com.huboot.business.base_model.login.sso.client.secruity.user.ApiUser;
import com.huboot.business.base_model.login.sso.client.secruity.user.JwtUser;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public final class UserFactory {

	private UserFactory() {
		
    }

    public static JwtUser create(BaseUser user, List<String> roles) {
        return new JwtUser(
                user.getGid(),
                user.getAccount(),
                user.getPassword(),
                user.getStatus(),
                mapToGrantedAuthorities(roles)
        );
    }
    
    public static ApiUser create(String requestId, String account, String password, List<String> roles) {
        return new ApiUser(requestId,account,password,mapToGrantedAuthorities(roles));
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
    
    public static BaseUser createFrozenUser(BaseUser user){
    	user.setStatus(BaseUser.UserStatus.frozen);
    	return user;
    }
    
    public static BaseUser createCancellationUser(BaseUser user){
    	user.setStatus(BaseUser.UserStatus.cancellation);
    	return user;
    }
}

