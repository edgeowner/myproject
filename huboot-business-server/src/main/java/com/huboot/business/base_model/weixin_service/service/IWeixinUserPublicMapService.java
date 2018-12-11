package com.huboot.business.base_model.weixin_service.service;

import com.huboot.business.base_model.weixin_service.dto.dto.WeixinAuthDTO;

/**
 * Created by Administrator on 2018/6/6 0006.
 */
public interface IWeixinUserPublicMapService {

    void createPublicMap(WeixinAuthDTO authDTO, String weixinUid);

}
