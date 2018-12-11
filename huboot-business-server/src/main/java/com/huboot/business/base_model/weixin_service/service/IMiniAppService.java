package com.huboot.business.base_model.weixin_service.service;


import com.huboot.business.base_model.weixin_service.dto.dto.WeixinAuthDTO;
import com.huboot.business.base_model.weixin_service.dto.weixin_center.dto.QrCodeDTO;
import com.huboot.business.base_model.weixin_service.dto.MiniappPagerDTO;
import com.huboot.business.base_model.weixin_service.dto.util.Pager;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/7 0007.
 */
public interface IMiniAppService {

    void youhua();

    /**
     * 获取小程序用户openid
     * @param weixinUid
     * @param wxcode
     * @return
     */
    WeixinAuthDTO getMiniAppOpenId(String weixinUid, String wxcode)  throws Exception;

    /**
     * 设置小程序域名配置
     * @return
     */
    void commitDomain(String weixinUid);


    void bitchCommitDomain();

    void commitViewDomain(String weixinUid);

    void bitchCommitViewDomain();

    /**
     *
     * @param weixinUid
     * @param version
     */
    void setWeappSupportVersion(String weixinUid, String version);



    /**
     * 获取小程序二维码
     * @param map
     * @return
     */
    QrCodeDTO getAndCreateMiniQrCode(@RequestBody Map<String, String> map);



    Pager<MiniappPagerDTO> miniappPager(String shopUid, String miniappUid, Integer page, Integer size);


    String getExperienceQrcode(String miniappUid, String path)  throws Exception;

    String getMiniappQrcode(String miniappUid) throws Exception;

    String getMiniPhone(String weixinUid, String encryptedData, String iv, String opendId) throws Exception;

}
