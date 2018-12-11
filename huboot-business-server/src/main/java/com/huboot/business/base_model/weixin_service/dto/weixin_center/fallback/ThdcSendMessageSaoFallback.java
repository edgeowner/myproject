package com.huboot.business.base_model.weixin_service.dto.weixin_center.fallback;

import com.huboot.business.base_model.weixin_service.dto.weixin_center.IThdcSendMessageSao;
import com.huboot.business.base_model.weixin_service.dto.weixin_center.dto.ThdcZKVmsMessageDTO;
import com.huboot.business.base_model.weixin_service.dto.weixin_center.dto.ThdcZKWeixinMessageDTO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Created by Administrator on 2018/2/5 0005.
 */
public class ThdcSendMessageSaoFallback implements IThdcSendMessageSao {

    @Override
    public void sendZKWeixinMessageToThdc(@RequestBody ThdcZKWeixinMessageDTO dto) {

    }

    @Override
    public void sendZKWeixinMessageToThdcList(List<ThdcZKWeixinMessageDTO> dto) {

    }

    @Override
    public void sendZKVmsMessageToThdc(@RequestBody ThdcZKVmsMessageDTO dto) {

    }
}
