package com.huboot.business.base_model.weixin_service.dto.weixin_center;


import com.huboot.business.base_model.weixin_service.dto.common.constant.ApplicationName;
import com.huboot.business.base_model.weixin_service.dto.weixin_center.dto.ZKWeixinMessageDTO;
import com.huboot.business.base_model.weixin_service.dto.weixin_center.fallback.WeixinSendMessageSaoFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by Administrator on 2018/2/5 0005.
 */
@FeignClient(name = ApplicationName.XIEHUA_SERVER, fallback = WeixinSendMessageSaoFallback.class)
public interface IWeixinSendMessageSao {


    @PostMapping(value = "/base_model/weixin_service/weixinPublicTempalte/sendZKWeixinMessage")
    public void sendZKWeixinMessage(@RequestBody ZKWeixinMessageDTO dto);

}
