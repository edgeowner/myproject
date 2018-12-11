package com.huboot.business.base_model.login.sso.client.secruity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


public class ApiUser implements UserDetails {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String requestId;//全局id(ssoId)
	
    private final String account;
    
    private final String password;
    
    private final Collection<? extends GrantedAuthority> authorities;

    public ApiUser(
            String requestId,
            String account,
            String password,
            Collection<? extends GrantedAuthority> authorities) {
        this.requestId = requestId;
        this.account = account;
        this.password = password;
        this.authorities = authorities;
    }

    
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return account;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

	public String getAccount() {
		return account;
	}
	
	public String getRequestId() {
		return requestId;
	}


	
}
