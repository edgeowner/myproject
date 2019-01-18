package com.huboot.user.weixin.service;


import com.huboot.commons.page.ShowPageImpl;
import com.huboot.share.user_service.api.dto.QrcodeCreateDTO;
import com.huboot.user.weixin.dto.WxmpUpdateDTO;
import com.huboot.user.weixin.dto.admin.WxmpPagerDTO;
import com.huboot.user.weixin.dto.wycshop.WxmpappInfoDTO;
import com.huboot.user.weixin.entity.WxmpEntity;

/**
 *公众号Service
 */
public interface IWxmpService {

    WxmpappInfoDTO findWxmpappInfo();

    WxmpappInfoDTO authAndBindShop(String authorizationCode);

    ShowPageImpl<WxmpPagerDTO> getPager(Integer page, Integer size);

    String createQrcode(QrcodeCreateDTO createDTO);

    String getQrcodeImg(String wxmpappId);

    void saveSubscribeReply(WxmpUpdateDTO wxmpUpdateDTO);

    WxmpEntity findWxmpEntityByShopId(Long shopId);
}
