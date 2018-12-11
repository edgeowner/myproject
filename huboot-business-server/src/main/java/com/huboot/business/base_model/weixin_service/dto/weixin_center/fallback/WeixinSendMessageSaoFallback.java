package com.huboot.business.base_model.weixin_service.dto.weixin_center.fallback;

import com.huboot.business.base_model.weixin_service.dto.weixin_center.IWeixinSendMessageSao;
import com.huboot.business.base_model.weixin_service.dto.weixin_center.dto.ZKWeixinMessageDTO;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by Administrator on 2018/2/5 0005.
 */
public class WeixinSendMessageSaoFallback implements IWeixinSendMessageSao {

    @Override
    public void sendZKWeixinMessage(@RequestBody ZKWeixinMessageDTO dto) {

    }
}
