package com.huboot.gateway.common;

import com.huboot.commons.sso.PermissionResourcesDTO;
import com.huboot.commons.sso.SSORedisHashName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
public class CheckAuthority {

    @Autowired
    private RedisTemplate redisTemplate;

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * @param authority
     * @return
     */
    public Boolean check(String authority, String requestUrl, HttpMethod httpMethod) {
        List<PermissionResourcesDTO> permissionResourcesDTOS = (List<PermissionResourcesDTO>) redisTemplate.opsForHash().get(SSORedisHashName.URL_PERMISSION, authority);
        if (CollectionUtils.isEmpty(permissionResourcesDTOS)) {
            return false;
        }
        Boolean result = permissionResourcesDTOS.stream().anyMatch(dto -> {
            for (String url : dto.getUrl()) {
                //匹配url-》匹配method
                if (pathMatcher.match(url, requestUrl)) {
                    for (String method : dto.getMethod()) {
                        if (method.equals("ALL")) {
                            return true;
                        } else {
                            if (httpMethod.matches(method)) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        });
        return result;
    }
}
