package com.huboot.user.weixin.service;


import com.huboot.commons.page.ShowPageImpl;
import com.huboot.share.user_service.api.dto.QrcodeCreateDTO;
import com.huboot.share.user_service.api.dto.UserAuthResultDTO;
import com.huboot.user.weixin.dto.admin.MiniappPagerDTO;
import com.huboot.user.weixin.dto.wycshop.MiniappInfoDTO;

/**
 *小程序Service
 */
public interface IMiniappService {

    //
    MiniappInfoDTO findMiniappInfo(Long shopId);

    //
    MiniappInfoDTO authAndBindShop(String authorizationCode, Long shopId);

    //获取openid
    UserAuthResultDTO getOpenId(Long shopId, String code);

    void setCanBitchRelease(String miniappId, String yesOrNo);

    String createQrcode(QrcodeCreateDTO createDTO);

    String createPathQrcode(QrcodeCreateDTO createDTO);

    ShowPageImpl<MiniappPagerDTO> getPager(Integer page, Integer size);

    String getQrcodeImg(String miniappId);

    String getExperienceQrcodeImg(String miniappId, String path)throws Exception;

}
