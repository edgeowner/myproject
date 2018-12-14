package com.huboot.business.base_model.orderhandler;

import com.huboot.business.base_model.weixin_service.entity.WeixinPublicMenuEntity;
import com.huboot.business.common.component.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by Administrator on 2018/6/11 0011.
 */
public abstract class AbstractRentOrderOperateHandler<DTO extends AbstractRentOrderOperateDTO>
        implements RentOrderOperateHandler {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private HandlerCompleteEventPublisherConfig publisherConfig;

    /**
     * 通过注入事件发布器来发布事件
     * @param publisherConfig
     */
    public void setPublisherConfig(HandlerCompleteEventPublisherConfig publisherConfig) {
        this.publisherConfig = publisherConfig;
    }

    /**
     * 有任务流程处理
     * @param orderEntity
     * @param map
     * @return
     * @throws BizException
     */
    @Override
    public RentOrderOperateResponse operateHandler(WeixinPublicMenuEntity orderEntity, Map<String, Object> map) throws BizException {
        DTO dto = this.beforeHandler(map);
        OrderOperateEvent event = new OrderOperateEvent();
        event.setOrderId(orderEntity.getId());
        event.setOperate(operate());
        RentOrderOperateResponse response = this.doHandler(orderEntity, dto, event);
        event.setOrder(orderEntity);
        if(publisherConfig != null) {
            this.publishEvent(event);
        } else {
            this.afterHandler(event);
        }
        return response;
    }

    /**
     *  转换和校验参数
     * @param map
     * @return
     */
    public abstract DTO beforeHandler(Map<String, Object> map);

    /**
     * 子类实现具体业务实现逻辑
     * @param orderEntity
     * @param operateDTO
     * @param event
     */
    public abstract RentOrderOperateResponse doHandler(WeixinPublicMenuEntity orderEntity, DTO operateDTO, OrderOperateEvent event);


    /**
     * 发布事件
     * @param event
     */
    private void publishEvent(final OrderOperateEvent event) {
        publisherConfig.getOrderAsync().execute(() -> {
            afterHandler(event);
        });
    }


    private void afterHandler(final OrderOperateEvent event) {
        try {
            this.afterHandlerComplete(event);
        } catch (Exception e) {
            logger.error("订单处理afterHandlerComplete错误，operate={}，sn={}", operate(), event.getOrderSn(), e);
        }
    }

    /**
     * 监听事件处理
     * @param event
     */
    @Transactional
    public void afterHandlerComplete(final OrderOperateEvent event) {}
}
