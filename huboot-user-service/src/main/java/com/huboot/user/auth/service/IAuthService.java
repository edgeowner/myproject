package com.huboot.user.auth.service;


import com.huboot.commons.component.auth.JwtClaims;
import com.huboot.commons.component.exception.BizException;
import com.huboot.user.auth.dto.*;
import com.huboot.user.auth.dto.wycminiapp.UserCustomerWeixinPhoneNumberAddReqDTO;
import com.huboot.user.auth.dto.wycminiapp.WycMiniappLoginInfoResDTO;
import com.huboot.user.auth.dto.wycshop.WycShopLoginInfoResDTO;
import com.huboot.user.auth.dto.zkshop.ZkShopLoginInfoResDTO;
import com.huboot.user.auth.dto.zkuser.ZkUserLoginInfoResDTO;

import com.huboot.user.auth.dto.admin.AdminLoginInfoResDTO;

/**
 * 用户服务-认证Service
 */
public interface IAuthService {


    /**
     * @return
     * @throws BizException
     */
    JwtClaims loginAdmin(UsernamePasswordAuthenticationReqDTO dto) throws BizException;
    JwtClaims loginWycMiniapp(UsernamePasswordAuthenticationReqDTO dto) throws BizException;
    JwtClaims loginWycShop(UsernamePasswordAuthenticationReqDTO dto) throws BizException;
    JwtClaims loginZkshop(UsernameCodeAuthenticationReqDTO dto) throws BizException;
    JwtClaims loginZkUser(UsernameCodeAuthenticationReqDTO dto) throws BizException;
    void logoutZkshop() throws BizException;
    void smsSendZkshop(UsernameCodeSendReqDTO dto) throws BizException;
    void smsSendZkUser(UsernameCodeSendReqDTO dto) throws BizException;
    void logoutZkUser() throws BizException;

    /**
     * @return
     * @throws BizException
     */
    JwtClaims wxLogin(ThirdAuthenticationReqDTO dto) throws BizException;
    JwtClaims wxLoginForZkShop(ThirdAuthenticationReqDTO dto) throws BizException;
    JwtClaims wxLoginForZkUser(ThirdAuthenticationReqDTO dto) throws BizException;
    JwtClaims weinxinPhoneNumber(UserCustomerWeixinPhoneNumberAddReqDTO dto) throws BizException;
    JwtClaims weinxinPhoneNumberForZkShop(UserCustomerWeixinPhoneNumberAddReqDTO dto) throws BizException;
    JwtClaims weinxinPhoneNumberForZkUser(UserCustomerWeixinPhoneNumberAddReqDTO dto) throws BizException;

    void updatePassword(UpdatePasswordReqDTO dto) throws BizException;

    AdminLoginInfoResDTO findAdminLoginInfo() throws BizException;

    WycMiniappLoginInfoResDTO findWycMiniAppLoginInfo() throws BizException;
    ZkShopLoginInfoResDTO findZkShopLoginInfo() throws BizException;
    ZkUserLoginInfoResDTO findZkUserLoginInfo() throws BizException;

    WycShopLoginInfoResDTO findWycShopLoginInfo() throws BizException;
}
