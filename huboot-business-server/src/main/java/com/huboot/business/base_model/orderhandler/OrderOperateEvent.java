package com.huboot.business.base_model.orderhandler;

import com.huboot.business.base_model.weixin_service.entity.WeixinPublicMenuEntity;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/4 0004.
 */
@Data
public class OrderOperateEvent {

    private Integer orderId;

    private String orderSn;

    private WeixinPublicMenuEntity order;

    private String operate;

    private Map<String, Object> attr = new HashMap<>();

    public void setAttribute(String key, Object value) {
        attr.put(key, value);
    }

    public <T> T getAttribute(String key, Class<T> clazz) {
        return (T)attr.get(key);
    }
}
