package com.huboot.business.mybatis;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

/**
 * @ClassName: SecurityContextHelper
 * @Description: SecurityContext工具类
 */
public class SecurityContextHelper {

    /**
     * 用户是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object detail = authentication.getDetails();
            if (detail instanceof User) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取当前登录用户ID，如果ID存在
     *
     * @return
     */
    public static Long getUserIdIfExits() {
        Long userId = getCurrentUser().getUserId();
        AssertUtil.notNull(userId, "无法获取当前用户标识信息");
        return userId;
    }

    /**
     * 获取当前登录用户的CustomerID，如果ID存在
     *
     * @return
     */
    public static Long getCustomerIdIfExits() {
        Long customerId = getCurrentUser().getLoginCustomer().getCustomerId();
        AssertUtil.notNull(customerId, "无法获取当前用户账户信息");
        return customerId;
    }

    /**
     * 获取当前登录用户的购物车CartId，如果ID存在
     *
     * @return
     */
    public static Long getCartIdIfExits() {
        Long cartId = getCurrentUser().getLoginCustomer().getCartId();
        AssertUtil.notNull(cartId, "无法获取当前用户购物车信息");
        return cartId;
    }

    /**
     * 获取当前登录用户的CompanyID，如果ID存在
     *
     * @return
     */
    public static Long getCompanyIdIfExits() {
        Long companyId = getCurrentUser().getLoginCustomer().getCompanyId();
        AssertUtil.notNull(companyId, "无法获取当前用户公司信息");
        return companyId;
    }

    /**
     * 获取当前登录用户的ShopID，如果ID存在
     *
     * @return
     */
    public static Long getShopIdIfExits() {
        Long shopId = getCurrentUser().getLoginCustomer().getShopId();
        AssertUtil.notNull(shopId, "无法获取当前用户店铺信息");
        return shopId;
    }

    /**
     * 获取当前登录用户的cityAreaId，如果ID存在
     *
     * @return
     */
    public static Long getCityAreaIdIfExits() {
        Long areaId = getCurrentUser().getLoginCustomer().getCityAreaId();
        AssertUtil.notNull(areaId, "无法获取当前用户区域信息");
        return areaId;
    }

    /**
     * 获取当前登录用户的phone，如果ID存在
     *
     * @return
     */
    public static String getPhoneIfExits() {
        String phone = getCurrentUser().getPhone();
        AssertUtil.notNull(phone, "无法获取当前用户区域信息");
        return phone;
    }

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User detail = null;
        if (authentication != null) {
            try {
                detail = (User) authentication.getDetails();
            } catch (Exception e) {
                throw new ApiException(ErrorCodeEnum.SESSIONISEXPIRED, "Session已经过期，请重新登录！");
            }
        }
        AssertUtil.notNull(detail, "无法获取当前用户信息");
        return detail;
    }

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    public static User getCurrentUserWithNull() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User detail = null;
        if (authentication != null) {
            try {
                detail = (User) authentication.getDetails();
            } catch (Exception e) {
                throw new ApiException(ErrorCodeEnum.SESSIONISEXPIRED, "Session已经过期，请重新登录！");
            }
        }
        return detail;
    }

    /**
     * 获取当前登录用户的所有角色
     *
     * @return
     */
    public static Collection<? extends GrantedAuthority> getCurrentUserAuthorities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities;
    }

    /**
     * 注销
     */
    public static void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    /**
     * 设置当前登录user用户信息--仅用于登陆
     *
     * @return
     */
    public static void setCurrentUser(User user) {
        if (user != null) {
            try {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
                authentication.setDetails(user);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                throw new ApiException(ErrorCodeEnum.SESSIONISEXPIRED);
            }
        }
    }

    /**
     * 设置当前登录user用户信息--仅用于登陆
     *
     * @return
     */
    public static void setCurrentUser(SimpleGrantedAuthority simpleGrantedAuthority) {
        if (simpleGrantedAuthority != null) {
            try {
                User user = getCurrentUser();
                Collection<GrantedAuthority> auths = user.getAuthorities();
                auths.add(simpleGrantedAuthority);
                user.setAuthorities(auths);
                setCurrentUser(user);
            } catch (Exception e) {
                throw new ApiException(ErrorCodeEnum.SESSIONISEXPIRED);
            }
        }
    }

    /**
     * 设置当前登录customer用户信息
     *
     * @return
     */
    public static void setCurrentUser(LoginCustomer loginCustomer) {
        if (loginCustomer != null) {
            try {
                User user = getCurrentUser();
                user.setLoginCustomer(loginCustomer);
                UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
                authentication.setDetails(user);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                throw new ApiException(ErrorCodeEnum.SESSIONISEXPIRED);
            }
        }
    }

    /**
     * 设置当前登录用户信息
     *
     * @return
     */
    public static void setCurrentUser(LoginAdmin loginAdmin) {
        if (loginAdmin != null) {
            try {
                User user = getCurrentUser();
                user.setLoginAdmin(loginAdmin);
                UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
                authentication.setDetails(user);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                throw new ApiException(ErrorCodeEnum.SESSIONISEXPIRED);
            }
        }
    }
}
