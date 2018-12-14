package com.huboot.business.base_model.orderhandler;


import com.huboot.business.base_model.weixin_service.entity.WeixinPublicMenuEntity;
import com.huboot.business.common.utils.JsonUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Administrator on 2018/8/14 0014.
 */
@Component
public class FinishOrderHandler extends AbstractRentOrderOperateHandler<FinishDTO> {

    @Override
    public String operate() {
        return FINISH_ORDER;
    }


    /*使用：
    入口：
    @PostMapping(value = "/base_model/order_center/zk/new/{operate}/{findtype}/{sn}")
    @ApiOperation(response = void.class, value = "订单操作")
    public RentOrderOperateResponse operate(@PathVariable("operate")String operate, @PathVariable("findtype")String findtype,
                                              @PathVariable("sn")String sn, @RequestBody Map map) throws Exception {
        return zKRentOrderService.orderOperateHandler(sn, findtype, operate, map);;---------------------------------------------------------------------1
    }



      方法zKRentOrderService.orderOperateHandler(sn, findtype, operate, map)：
    @Autowired
    private SpringContext springContext;

    Map<String, RentOrderOperateHandler> beanMap = springContext.getContext().getBeansOfType(RentOrderOperateHandler.class);
    Optional<RentOrderOperateHandler> optional = beanMap.values().stream().filter(bean -> operate.equals(bean.operate())).findFirst();
if(optional.isPresent()) {
        RentOrderOperateHandler handler = optional.get();
        return handler.operateHandler(orderEntity, map);---------------------------------------------------------------------2
    } else {
        throw new BizException("直客订单操作无处理类");
    }
*/


    @Override
    public FinishDTO beforeHandler(Map<String, Object> map) {
        FinishDTO dto = JsonUtils.fromMapToObject(map, FinishDTO.class);
        return dto;
    }

    @Override
    public RentOrderOperateResponse doHandler(WeixinPublicMenuEntity zKRentOrderEntity, FinishDTO operateDTO, OrderOperateEvent event) {
        //一些确认完成的处理逻辑
        return null;
    }

    @Override
    public void afterHandlerComplete(OrderOperateEvent event) {
        //确认完成后，发一些通知，短信
    }
}
