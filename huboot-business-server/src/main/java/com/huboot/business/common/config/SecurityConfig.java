

package com.huboot.business.common.config;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.huboot.business.base_model.login.sso.client.config.JwtConfig;
import com.huboot.business.base_model.login.sso.client.dto.BaseUser;
import com.huboot.business.base_model.login.sso.client.dto.login.RequestHeader;
import com.huboot.business.base_model.login.sso.client.dto.login.inter.ILogin;
import com.huboot.business.base_model.login.sso.client.interceptor.ClientInfoInterceptor;
import com.huboot.business.base_model.login.sso.client.interceptor.RequestInfo;
import com.huboot.business.base_model.login.sso.client.secruity.JwtAccessDeniedHandler;
import com.huboot.business.base_model.login.sso.client.secruity.JwtAuthenticationEntryPoint;
import com.huboot.business.base_model.login.sso.client.secruity.filter.ApiAuthenticationTokenFilter;
import com.huboot.business.base_model.login.sso.client.secruity.filter.JwtAuthenticationTokenFilter;
import com.huboot.business.base_model.login.sso.client.utils.RequestUtil;
import com.huboot.business.base_model.login.sso.client.utils.WebUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by wjc on 2016/11/3.
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationTokenFilter userAuthenticationTokenFilter;

    @Autowired
    private ApiAuthenticationTokenFilter apiAuthenticationTokenFilter;

    @Autowired
    private JwtConfig jwtConfig;


    /*登录的逻辑：
    @PostMapping("/wexin_b2c_app/b2cUser/auth")
    @ApiOperation(value = "登录", notes = "登录")
    @ApiResponses(value = {@ApiResponse(code = 200, response = MiniAppUserLoginRespDTO.class, message = "OK")})
    public MiniAppUserLoginRespDTO login(@Validated({ILogin.class}) @RequestBody LoginReqDTO loginReqDTO, HttpServletRequest request, HttpServletResponse response) throws IOException, RuntimeException, ClassNotFoundException, IllegalAccessException, UnsupportedEncodingException, JsonProcessingException {
        RequestHeader requestHeader = RequestUtil.getJwtSignRequestHeader(request);
        //login
        String jwtToken = b2cUserService.login(loginReqDTO, requestHeader, false);
        response.setHeader(tokenHeader, tokenHead + jwtToken);
     }

        登录的方法：
        public String login(@Validated({ILogin.class}) LoginReqDTO loginReqDTO, RequestHeader requestHeader, boolean autoLogin) throws RuntimeException, ClassNotFoundException, IllegalAccessException, UnsupportedEncodingException, JsonProcessingException {
        //get and check shop uid
        String shopUid = (String) RequestInfo.get(ClientInfoInterceptor.SHOP_UID);

        //各种校验，通过拿取user数据，角色数据，请求头，最终调用jwtTokenComponent.generateToken

        //sso login
        BaseUser user = ssoSerService.login(loginDTO);
        //grant authorization
        List<String> roles = new ArrayList<>();
        roles.add(roleEntity.getRoleName());
        roles.add(SecurityConfig.SecurityRoleEnum.role_b2c_user_common.getFullRole());
        Map<String, String> headerMap = WebUtil.beanToMapWithString(requestHeader, false);
        String token = jwtTokenComponent.generateToken(user, headerMap, roles, userEntity.getId() + "");
        return token;

        */



    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean(name = "bCryptPasswordEncoder")
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name ="standardPasswordEncoder")
    public PasswordEncoder standardPasswordEncoder() {
        return new StandardPasswordEncoder( "zchz-secret-key");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        List<String> urls = jwtConfig.getPermit().stream().map(s -> s.getUrl()).collect(Collectors.toList());
        httpSecurity
                // 由于使用的是JWT，我们这里不需要csrf
                .csrf().disable()
                .httpBasic().disable()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // 允许对于网站静态资源的无授权访问
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
                .antMatchers("/wexin_b2c_app/homeicon/**").permitAll()
                .antMatchers(urls.toArray(new String[urls.size()])).permitAll()  //permitAll
                .antMatchers("/base_model/**").permitAll()
                .antMatchers("/wexin_b2c_app/user_home/**","/wexin_b2c_app/weixin/**","/shopping_car/**").hasRole(SecurityRoleEnum.role_b2c_user_common.role) //直客用户中心,//置换微信code//购物车
                //小程序权限配置
                .antMatchers("/weixin_mini_app/miniAppBox/**").hasRole(SecurityRoleEnum.role_miniapp_user_common.role) //小程序用户权限
                //商家后台(web)
                .antMatchers("/business_manage_web/zk/**").hasRole(SecurityRoleEnum.role_business_manage_zk_web.role)//直客
                .antMatchers("/business_manage_web/box/**").hasRole(SecurityRoleEnum.role_business_manage_box_web.role)//盒子
                .antMatchers("/business_manage_web/association/**").hasRole(SecurityRoleEnum.role_business_manage_association_web.role)//协会
                .anyRequest().authenticated();
        // 添加JWT filter
        //httpSecurity.addFilterBefore(apiAuthenticationTokenFilter, JwtAuthenticationTokenFilter.class);
        httpSecurity.addFilterBefore(userAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // httpSecurity.addFilter(filter)
        httpSecurity.addFilterBefore(apiAuthenticationTokenFilter, JwtAuthenticationTokenFilter.class);
        // 禁用缓存
        //httpSecurity.headers().cacheControl();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //allow Swagger URL to be accessed without authentication
        web.ignoring().antMatchers(
                "/actuator/**",
                "/application/**",
                "/error",
                "/webjars/**",
                "/favicon.ico",
                "/v2/api-docs",//swagger api json
                "/swagger-resources/configuration/ui",//用来获取支持的动作
                "/swagger-resources",//用来获取api-docs的URI
                "/swagger-resources/configuration/security",//安全选项
                "/swagger-ui.html");
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public enum SecurityRoleEnum {

        role_b2c_user_common("B2C_USER_COMMON","ROLE_B2C_USER_COMMON",""),
        role_miniapp_user_common("MINIAPP_USER_COMMON","ROLE_MINIAPP_USER_COMMON",""),
        role_b2b_user_common("B2B_USER_COMMON","ROLE_B2B_USER_COMMON",""),
        role_b2b_shop("B2B_SHOP","ROLE_B2B_SHOP",""),
        role_business_manage_zk_web("BUSINESS_MANAGE_ZK_WEB","ROLE_BUSINESS_MANAGE_ZK_WEB","(web)"),
        role_business_manage_box_web("BUSINESS_MANAGE_BOX_WEB","ROLE_BUSINESS_MANAGE_BOX_WEB","(web)"),
        role_business_manage_association_web("BUSINESS_MANAGE_ASSOCIATION_WEB","ROLE_BUSINESS_MANAGE_ASSOCIATION_WEB","(web)")
        ;

        private String role;

        private String fullRole;

        private String showName;

    }

}

