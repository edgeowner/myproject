package com.huboot.business.base_model.orderhandler;

import com.huboot.business.base_model.weixin_service.entity.WeixinPublicMenuEntity;
import com.huboot.business.common.component.exception.BizException;

import java.util.Map;

/**
 * Created by Administrator on 2018/6/11 0011.
 */
public interface RentOrderOperateHandler {

    //确认完成
    String FINISH_ORDER = "finish_order";

    /**
     * 操作类型
     * @return
     */
    String operate();


    /**
     * 操作
     * @param orderEntity
     * @param map
     * @return
     * @throws BizException
     */
    RentOrderOperateResponse operateHandler(WeixinPublicMenuEntity orderEntity, Map<String, Object> map) throws BizException;

}
