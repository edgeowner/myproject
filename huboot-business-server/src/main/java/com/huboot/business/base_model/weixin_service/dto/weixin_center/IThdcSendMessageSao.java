package com.huboot.business.base_model.weixin_service.dto.weixin_center;

import com.huboot.business.base_model.weixin_service.dto.common.constant.ApplicationName;
import com.huboot.business.base_model.weixin_service.dto.weixin_center.dto.ThdcZKVmsMessageDTO;
import com.huboot.business.base_model.weixin_service.dto.weixin_center.dto.ThdcZKWeixinMessageDTO;
import com.huboot.business.base_model.weixin_service.dto.weixin_center.fallback.ThdcSendMessageSaoFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Created by Administrator on 2018/2/5 0005.
 */
@FeignClient(name = ApplicationName.THDC_SERVER, fallback = ThdcSendMessageSaoFallback.class)
public interface IThdcSendMessageSao {


    /**
     * 直客通知出租方
     * @param dto
     */
    @PostMapping(value = "/v1/order/b2c/notifySellerForZK")
    public void sendZKWeixinMessageToThdc(@RequestBody ThdcZKWeixinMessageDTO dto);

    /**
     * 直客通知出租方（多个商户）
     * @param dto
     */
    @PostMapping(value = "/v1/order/b2c/notifySellerForZK/list")
    public void sendZKWeixinMessageToThdcList(@RequestBody List<ThdcZKWeixinMessageDTO> dto);

    /**
     * 直客通知出租方
     * @param dto
     */
    @PostMapping(value = "/v1/system/vms/send")
    public void sendZKVmsMessageToThdc(@RequestBody ThdcZKVmsMessageDTO dto);

}
