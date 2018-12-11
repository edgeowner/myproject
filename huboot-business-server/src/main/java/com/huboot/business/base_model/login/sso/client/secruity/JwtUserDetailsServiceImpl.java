package com.huboot.business.base_model.login.sso.client.secruity;

import com.huboot.business.base_model.login.sso.client.component.JwtTokenComponent;
import com.huboot.business.base_model.login.sso.client.dto.BaseUser;
import com.huboot.business.base_model.login.sso.client.service.JwtUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
	
    @Autowired
    private JwtUserService jwtUserService;
    
    @Autowired
    private JwtTokenComponent jwtTokenComponent;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BaseUser user = jwtUserService.getAccountInfoByAccount(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return UserFactory.create(user,jwtTokenComponent.getUserRoles(user.getGid()));
        }
    }
}
